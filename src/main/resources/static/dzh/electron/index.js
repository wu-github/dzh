const { app, BrowserWindow } = require('electron')
const path = require('path');

let mainWin;

function createWindow() {
    mainWin = new BrowserWindow({
        width: 800,
        height: 600,
        webPreferences: {
            preload: path.join(__dirname, 'preload.js')
        }
    });
    // mainWin.openDevTools({ mode: 'bottom' });
    mainWin.loadFile('build/index.html');
}

app.whenReady().then(() => {
    createWindow();
    app.on('activate', function() {
        if (BrowserWindow.getAllWindows().length === 0) {
            createWindow();
        }
    })
})

app.on('window-all-closed', function() {
    if (process.platform !== 'darwin') {
        app.quit();
    }
})