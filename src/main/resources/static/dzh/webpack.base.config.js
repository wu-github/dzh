const HtmlWebpackPlugin = require('html-webpack-plugin');
const { CleanWebpackPlugin } = require("clean-webpack-plugin")
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
    output: {
        path: path.join(__dirname, 'build'),
        filename: 'dist/main.js',
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
            },
            {
                test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
                loader: 'url-loader',
                options: {
                    esModule: false,
                    limit: 2048,
                    name: 'public/image/[name].[hash:7].[ext]'
                }
            }
            // {
            //     test: /\.(woff2?|eot|ttf|otf)(\?.*)?$/,
            //     loader: 'url-loader',
            //     options: {
            //         esModule: false,
            //         limit: 2048,
            //         name: 'public/fonts/[name].[hash:7].[ext]'
            //     }
            // }
        ]
    },
    plugins: [
        new CleanWebpackPlugin(),
        new HtmlWebpackPlugin({
            template: path.join(__dirname, 'index.html'),
            filename: path.join(__dirname, 'build/index.html'),
        }),
        new VueLoaderPlugin(),
        Components({
            resolvers: [ElementPlusResolver()],
        }),
        new copyWebpackPlugin({
            patterns: [{
                from: path.join(__dirname, 'static/public'),
                to: path.join(__dirname, 'build/public'),
                toType: 'dir'
            }]
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
}