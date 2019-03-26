#!/bin/bash

git -C tool add .
git -C tool stash
git -C tool stash drop
