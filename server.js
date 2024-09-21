import express from 'express';
import path from 'path';
import bodyParser from 'body-parser';
import { fileURLToPath } from 'url';
import { createReadStream } from 'fs';
import csv from 'csv-parser';
import { exec } from 'child_process';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const app = express();
const port = 3000;
app.use(bodyParser.urlencoded({ extended: true }));
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));
app.use(express.static(path.join(__dirname, 'public')));
function loadCities() {
    const cities = [];
    const csvFilePath = path.join(__dirname, 'public', 'cities.csv');
    return new Promise((resolve, reject) => {
        createReadStream(csvFilePath)
            .pipe(csv())
            .on('data', (row) => {
                cities.push({ name: row.name });
            })
            .on('end', () => {
                resolve(cities);
            })
            .on('error', (err) => {
                reject(err);
            });
    });
}
app.get('/', async (req, res) => {
    try {
        const cities = await loadCities();
        res.render('index', { cities, path: null });
    } catch (error) {
        console.error('Error loading cities:', error);
        res.status(500).send('Error loading cities');
    }
});

app.post('/', async (req, res) => {
    const { src, dst, type } = req.body;

    try {
        // Get the absolute path to the TripPlanner directory (project root)
        const projectRoot = path.join(__dirname);  // This is the root directory of the project
        
        // Java command with the classpath set to the project root, and the fully qualified class name
        const command = `java -cp ${projectRoot} scripts.Main ${src} ${dst} ${type}`;
        
        console.log(`Executing command: ${command}`);

        // Execute the Java program with child_process.exec
        exec(command, (error, stdout, stderr) => {
            if (error) {
                console.error(`Error executing Java program: ${stderr}`);
                res.status(500).send(`Error calculating the route: ${stderr}`);
            } else {
                console.log(`Java stdout: ${stdout}`);
                const cities = loadCities();  // Reload cities
                res.render('index', { cities, path: stdout });  // Send the result to the template
            }
        });
    } catch (error) {
        console.error('Error processing the request:', error);
        res.status(500).send('Error processing the request');
    }
});
app.listen(port, () => {
    console.log(`Server is running on http://localhost:${port}`);
});
