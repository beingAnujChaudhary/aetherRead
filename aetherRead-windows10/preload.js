const { contextBridge } = require('electron');

// We can expose safe, isolated APIs to the renderer process here
contextBridge.exposeInMainWorld('electronAPI', {
  // Add native bindings if necessary
});

window.addEventListener('DOMContentLoaded', () => {
  // Preload script initialized
});
