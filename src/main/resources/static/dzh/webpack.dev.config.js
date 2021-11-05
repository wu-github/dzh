const { merge } = require('webpack-merge')
const path = require('path');
const baseWebpackConfig = require('./webpack.base.config')

module.exports = merge(baseWebpackConfig, {
    devtool: 'inline-source-map', //nosources-cheap-module-source-map;nosources-source-map;inline-source-map//"^(inline-|hidden-|eval-)?(nosources-)?(cheap-(module-)?)?source-map$"
    devServer: {
        static: [
            path.join(__dirname, 'server'),
            path.join(__dirname, 'static'),
            path.join(__dirname)
        ]
    },

})