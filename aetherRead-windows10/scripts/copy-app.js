const fs = require('fs-extra');
const path = require('path');

const srcDir = path.join(__dirname, '..', '..', 'aetherRead-web', 'out');
const destDir = path.join(__dirname, '..', 'app');

async function copyApp() {
  try {
    console.log(`Copying built web app from ${srcDir} to ${destDir}...`);
    
    // Ensure destination exists and is clean
    if (fs.existsSync(destDir)) {
      await fs.remove(destDir);
    }
    
    // Copy out directory
    await fs.copy(srcDir, destDir);
    
    console.log('Successfully copied web app for Windows packaging.');
  } catch (err) {
    console.error('Error copying app:', err);
    process.exit(1);
  }
}

copyApp();
