import firebase from 'firebase/compat/app';
import 'firebase/compat/firestore';

const firebaseConfig = {
    apiKey: "",
    authDomain: "intermodular-joi.firebaseapp.com",
    projectId: "intermodular-joi",
    storageBucket: "intermodular-joi.firebasestorage.app",
    messagingSenderId: "103805860851",
    appId: "1:103805860851:web:6f757f6c549cbb20fbb086"
};

if (!firebase.apps.length) {
  firebase.initializeApp(firebaseConfig);
}

export const firestore = firebase.firestore();