#!/usr/bin/env bash
java -jar build/libs/persistence-vault.jar db migrate src/main/resources/application.yaml
