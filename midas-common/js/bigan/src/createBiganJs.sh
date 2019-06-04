rm bigan.js
rm bigan-min.js
cat bigan*.js >> bigan.js
uglifyjs bigan.js -o bigan-min.js
cp bigan.js ..
cp bigan-min.js ..

