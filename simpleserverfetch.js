"use strict";
const fetch = require('node-fetch');
let url = 'http://192.168.0.103:8888';

const getData = async url => {
    try {
        const response = await fetch(url, {method:"post"});
        let res = await response.text();
        return res;
    } catch (error) {
        return error;
    }

};


getData(url).then(result=>console.log(result),
    error=>console.log(error));