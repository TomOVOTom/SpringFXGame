package com.example.demo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Demo2Application extends Application {

    private double playerX = 50;
    private double playerY = 50;
    private double playerSize = 50;
    private List<double[]> obstacles = new ArrayList<>();
    private int score = 0;
    private boolean gameOver = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simple Game");

        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Scene scene = new Scene(new StackPane(canvas));
        scene.setOnKeyPressed(e -> {
            if (!gameOver) {
                if (e.getCode() == KeyCode.LEFT) {
                    playerX -= 10;
                } else if (e.getCode() == KeyCode.RIGHT) {
                    playerX += 10;
                } else if (e.getCode() == KeyCode.UP) {
                    playerY -= 10;
                } else if (e.getCode() == KeyCode.DOWN) {
                    playerY += 10;
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();

        new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 100_000_000) {
                    updateGame();
                    lastUpdate = now;
                }
                renderGame(gc);
            }
        }.start();
    }

    private void updateGame() {
        if (gameOver) {
            return;
        }

        // Add new obstacles
        if (Math.random() < 0.1) {
            obstacles.add(new double[]{800, new Random().nextInt(600), 20, 20});
        }

        // Move obstacles
        for (double[] obstacle : obstacles) {
            obstacle[0] -= 5;
        }

        // Check for collisions
        for (double[] obstacle : obstacles) {
            if (playerX < obstacle[0] + obstacle[2] && playerX + playerSize > obstacle[0] &&
                    playerY < obstacle[1] + obstacle[3] && playerY + playerSize > obstacle[1]) {
                gameOver = true;
            }
        }

        // Remove off-screen obstacles
        obstacles.removeIf(obstacle -> obstacle[0] < -20);

        // Update score
        score++;
    }

    private void renderGame(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 800, 600);

        if (gameOver) {
            gc.setFill(Color.RED);
            gc.fillText("Game Over! Score: " + score, 350, 300);
        } else {
            gc.setFill(Color.LIGHTBLUE);
            gc.fillRect(playerX, playerY, playerSize, playerSize);

            gc.setFill(Color.LIGHTGRAY);
            for (double[] obstacle : obstacles) {
                gc.fillRect(obstacle[0], obstacle[1], obstacle[2], obstacle[3]);
            }

            gc.setFill(Color.BLACK);
            gc.fillText("Score: " + score, 10, 20);
        }
    }
}