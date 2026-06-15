import { initializeApp, getApps } from 'firebase/app';
import { getAuth } from 'firebase/auth';

const firebaseConfig = {
  apiKey: 'AIzaSyCfxuIccvc4OfAXI8AiPLHgcvo9WEJmTO0',
  authDomain: 'beinganujchaudhary-dfb74.firebaseapp.com',
  projectId: 'beinganujchaudhary-dfb74',
  storageBucket: 'beinganujchaudhary-dfb74.firebasestorage.app',
  messagingSenderId: '798601267553',
  appId: '1:798601267553:web:2fd3fa7eae29a4dbad5d20',
  measurementId: 'G-ED7BSQLR6T',
};

// Prevent duplicate initialization in Next.js dev mode
const app = getApps().length === 0 ? initializeApp(firebaseConfig) : getApps()[0];
export const auth = getAuth(app);
export default app;
