/**
 * @api {get} /ping Server Ping - get a reply when server is available
 * @apiName ServerPing
 * @apiGroup Misc/Tools
 * @apiSuccess {Object} ping object
 * @apiSuccessExample Success-Response:
 * HTTP/1.1 200 OK
 {
   "error": false,
   "message": "OK_200",
   "status": {
     "status": 200,
     "reasonPhrase": "OK",
     "reasonPhraseBytes": [
       79,
       75
     ],
     "statusBytes": [
       50,
       48,
       48
     ]
   }
 }
 */