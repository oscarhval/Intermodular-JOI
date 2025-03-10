import { ApplicationConfig, provideZoneChangeDetection, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { AngularFireModule } from '@angular/fire/compat';
import { AngularFirestoreModule } from '@angular/fire/compat/firestore';
import { initializeApp, provideFirebaseApp } from '@angular/fire/app';
import { getFirestore, provideFirestore } from '@angular/fire/firestore';
import { getDatabase, provideDatabase } from '@angular/fire/database';

const firebaseConfig = {
  apiKey: "AIzaSyD8L3our6X-TQOO4I2RZ-rQLQPwD78fgPk",
  authDomain: "intermodular-joi.firebaseapp.com",
  projectId: "intermodular-joi",
  storageBucket: "intermodular-joi.firebasestorage.app",
  messagingSenderId: "103805860851",
  appId: "1:103805860851:web:c3139bedb7b19556fbb086"
};

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    importProvidersFrom(
      AngularFireModule.initializeApp(firebaseConfig),
      AngularFirestoreModule
    ), provideFirebaseApp(() => initializeApp({ projectId: "intermodular-joi", appId: "1:103805860851:web:c3139bedb7b19556fbb086", storageBucket: "intermodular-joi.firebasestorage.app", apiKey: "AIzaSyD8L3our6X-TQOO4I2RZ-rQLQPwD78fgPk", authDomain: "intermodular-joi.firebaseapp.com", messagingSenderId: "103805860851" })), provideFirestore(() => getFirestore()), provideDatabase(() => getDatabase())
  ]
};