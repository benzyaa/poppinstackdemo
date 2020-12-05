const path = require('path');
//const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require("webpack");
module.exports = {
    mode: 'development',
    plugins: [
        /*new webpack.ContextReplacementPlugin(
            /\@angular(\\|\/)core(\\|\/)fesm5/,
            path.resolve(__dirname, 'src'),{}
        ),
        new HtmlWebpackPlugin({
            template: "src/index.html",
            inject: "body"
        }),*/
    ],
    optimization:{
      minimize: false,
      removeAvailableModules: false,
      removeEmptyChunks: false,
      splitChunks: false
      /*splitChunks: {
          chunks: 'all'
      }*/
    },
    resolve: {
        extensions: ['.js', '.ts']
    },
  entry: {
        polyfills: "./ts/polyfills.ts",
        vendor: "./ts/vendor.ts",
        main: "./ts/main.ts"
       //loginpage:"./LoginPage.ts"
  },
  output: {
    path: path.resolve(__dirname, 'js/'),
    publicPath: "/",
    filename: "[name].js",
    chunkFilename: "[name].js"
  },
  module: {
    rules: [{
        test: /\.css$/,
        loader: ["style-loader", "css-loader"]
      },
      {
        test: /\.ts$/,
        loader: "awesome-typescript-loader"
      },
      {
        test: /\.ts$/,
        enforce: "pre",
        loader: 'tslint-loader'
      },
      {
        test: /\.scss$/,
        loader: ["style-loader", "css-loader?sourceMap", "sass-loader?sourceMap"]
      }
    ]
  }
};
