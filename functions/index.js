const functions = require('firebase-functions');

const admin = require('firebase-admin')
admin.initializeApp(functions.config().firebase)

exports.readAllGoals = functions.https.onCall((req, res) => {

    var db = admin.firestore();
    var data = []
    return db.collection("goalItems3").orderBy("ddl").get().then(snapshot=>{
        snapshot.forEach(doc => {
            data = data.concat(doc.data());
        });
        return JSON.stringify(data);
    }).catch(reason => {
        return JSON.stringify([]);
    })
})