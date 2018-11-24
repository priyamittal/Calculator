const path = require('path')
const webpack = require('webpack')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const PATHS = {
    src: path.join(__dirname, 'src'),
    dist: path.join(__dirname, 'dist'),
}

module.exports = {
    entry: './src/index.js',
    module: {
        rules: [
            {
                test: /\.(js|jsx)$/,
                exclude: /node_modules/,
                use: ['babel-loader']
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            filename: 'index.html',
            chunks: ['index'],
            template: path.join(PATHS.src, 'index.html'),
        }),
        new webpack.HotModuleReplacementPlugin(),
    ],
    resolve: {
        extensions: ['*', '.js', '.jsx']
    },
    output: {
        path: path.resolve(__dirname, 'dist'),
        publicPath: '/',
        filename: 'bundle.js'
    },
    devServer: {
        contentBase: './dist',
        port: 8301,
        disableHostCheck: true,
        proxy: {
            '/calculator': {
                target: 'http://localhost:8080',
                changeOrigin: true,
                logLevel: 'info',
                headers: {
                    'Access-Control-Allow-Origin': '*'
                }
            }
        }
    }
};