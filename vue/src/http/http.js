import axios from 'axios'
import Qs from 'qs'
const devApi = 'http://localhost:8080/api/';
axios.defaults.headers['Content-Type'] = 'application/x-www-form-urlencoded';
const instance = axios.create({
    baseURL: devApi,
    withCredentials: true,
});

instance.interceptors.request.use(function (config) {
    if (config.headers['Content-Type'] === 'application/x-www-form-urlencoded'){
        config.data = Qs.stringify(config.data);
    }
    return config;
  }, function (error) {
    return Promise.reject(error);
});

instance.interceptors.response.use(function (res) {
    return res;
}, function (error) {
    switch (error.response.status) {
        case 400:
            window.location.href = "login.html";
            break;
    }
    return Promise.reject(error);
});
export default instance;