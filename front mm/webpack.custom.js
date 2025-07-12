const NodePolyfillPlugin = require('node-polyfill-webpack-plugin');

module.exports = {
  plugins: [
    new NodePolyfillPlugin()
  ],
  experiments: {
    topLevelAwait: true,
  },
  resolve: {
    fallback: {
      os: require.resolve('os-browserify/browser'),
      // add other polyfills if needed
    }
  }
};
