/**
 * BukkitREST; documentation/api.doc.js
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.02.2022
 */

'use strict'; // https://www.w3schools.com/js/js_strict.asp

var path = require('path');
var { createDoc } = require('apidoc');

const createdDocumentation = createDoc({
    src: [path.resolve(__dirname, 'routes')],
    dest: path.resolve(__dirname, 'generated_sources'),
    silent: true,
});

if (typeof createdDocumentation !== 'boolean') {
    console.log('Documentation creation done');
} else {
    console.error('Documentation creation failed');
}
