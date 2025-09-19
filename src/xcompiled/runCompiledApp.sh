#!/bin/bash

cd "$HOME/IdeaProjects/gym-app/target" || exit 1

# Ejecutar la app con JavaFX
java --module-path "$HOME/javafx-sdk-25" \
     --add-modules javafx.controls,javafx.fxml \
     -jar gymApp-1.0-SNAPSHOT.jar

cd "$HOME/IdeaProjects/gym-app/src/xcompiled" || exit 1
