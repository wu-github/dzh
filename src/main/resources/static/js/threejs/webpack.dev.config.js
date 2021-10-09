const { merge } = require('webpack-merge')
const baseWebpackConfig = require('./webpack.base.config')

module.exports = merge(baseWebpackConfig, {
    devtool: 'inline-source-map', //nosources-cheap-module-source-map;nosources-source-map;inline-source-map//"^(inline-|hidden-|eval-)?(nosources-)?(cheap-(module-)?)?source-map$"

})