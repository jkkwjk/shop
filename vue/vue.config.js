module.exports = {
    pages: {
        index: {
            entry: 'src/pages/index/main',
            template: 'public/index.html',
        },
        login: {
            entry: 'src/pages/login/main',
            template: 'public/login.html',
        }
    },
    devServer: {
        port: 3030,
        // proxy: {
        //     '/api': {
        //         target: 'http://localhost:3030/'
        //     }
        // }
    }
};