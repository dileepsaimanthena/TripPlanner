import express from 'express';
import path from 'path';
import bodyParser from 'body-parser';
import { fileURLToPath } from 'url';
import { createReadStream } from 'fs';
import csv from 'csv-parser';
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const app = express();
const port = 3000;
const csvFilePath = path.join(__dirname, 'public', 'cities.csv');
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static(path.join(__dirname, 'public')));
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'index.html'));
});

app.get('/cities', (req, res) => {
    const cities = [];
    createReadStream(csvFilePath)
        .pipe(csv())
        .on('data', (row) => {
            cities.push({ name: row.name });
        })
        .on('end', () => {
            res.json(cities);
        })
        .on('error', (err) => {
            console.error('Error reading the CSV file:', err);
            res.status(500).json({ error: 'Error reading cities from CSV' });
        });
});
app.listen(port, () => {
    console.log(`Server is listening on port ${port}`);
});
