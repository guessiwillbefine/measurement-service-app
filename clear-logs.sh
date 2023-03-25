#!/bin/sh
echo 'clear root log folder?'
echo '(Y\N):'
read -r answer
if [ "$answer" = 'Y' ]; then rm -r ./logs/*
else echo 'skipped'
fi

echo 'clear ms-api log folder?'
echo '(Y\N):'
read -r answer2
if [ "$answer2" = 'Y' ]; then rm -r ./ms-api/logs/*
else echo 'skipped'
fi
echo 'done!'

echo 'clear ms-consumer log folder?'
echo '(Y\N):'
read -r answer2
if [ "$answer2" = 'Y' ]; then rm -r ./ms-consumer/logs/*
else echo 'skipped'
fi
echo 'done!'