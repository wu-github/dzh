const HtmlWebpackPlugin = require('html-webpack-plugin');
const copyWebpackPlugin = require('copy-webpack-plugin');
const path = require('path');

const Components = require('unplugin-vue-components/webpack')
const { ElementPlusResolver } = require('unplugin-vue-components/resolvers')
const VueLoaderPlugin = require('vue-loader/dist/plugin').default;

module.exports = {
    context: path.join(__dirname),
    entry: {
        'app': './src/index.js'
    },
    devtool: 'nosources-cheap-module-source-map', //nosources-source-map;nosources-cheap-module-source-map//"^(inline-|hidden-|eval-)?(nosources-)?(cheap-(module-)?)?source-map$"
    output: {
        path: path.join(__dirname, 'build/dist'),
        filename: 'main.js',
        clean: true,
    },
    target: ['web', 'es5'],
    module: {
        rules: [{
                test: /\.(js|ts)x?$/,
                exclude: '/node_modules/',
                use: ['babel-loader']
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            },
            {
                test: /\.vue$/,
                use: ['vue-loader'],
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: path.join(__dirname, 'index.html'),
            filename: path.join(__dirname, 'build/index.html'),
        }),
        new copyWebpackPlugin({
            patterns: [{
                from: path.join(__dirname, 'public'),
                to: path.join(__dirname, 'build/public'),
                toType: 'dir'
            }]
        }),
        new VueLoaderPlugin(),
        Components({
            resolvers: [ElementPlusResolver()],
        }),
    ],
    performance: {
        hints: 'warning', //hints: false,
        maxEntrypointSize: 100000000,
        maxAssetSize: 100000000,
        assetFilter: function(assetFilename) {
            return assetFilename.endsWith('.js');
        }
    },
    devServer: {
        static: [
            path.join(__dirname, 'server'),
            path.join(__dirname)
        ]
    },
}