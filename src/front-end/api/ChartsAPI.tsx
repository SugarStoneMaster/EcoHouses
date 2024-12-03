// src/api/ChartsAPI.ts

// Funzione per simulare l'ottenimento dei dati per il grafico
export const fetchChartData = (time: string) => {
    // Simuliamo i dati per i grafici
    const lineChartData = {
        day: [
            { label: '08:00', consumi: 10, media: 15 },
            { label: '09:00', consumi: 20, media: 18 },
            { label: '10:00', consumi: 30, media: 20 },
        ],
        month: [
            { label: 'Week 1', consumi: 100, media: 150 },
            { label: 'Week 2', consumi: 200, media: 180 },
            { label: 'Week 3', consumi: 150, media: 200 },
        ],
        year: [
            { label: 'Jan', consumi: 500, media: 600 },
            { label: 'Feb', consumi: 550, media: 650 },
            { label: 'Mar', consumi: 600, media: 700 },
        ],
    };

    // Simula i dati per il grafico a torta (energia consumata vs prodotta)
    const pieChartData = {
        day: [
            { name: 'Consumo', value: 40, color: 'rgba(255, 99, 132, 1)', legendFontColor: '#000', legendFontSize: 15 },
            {
                name: 'Prodotto',
                value: 60,
                color: 'rgba(75, 192, 192, 1)',
                legendFontColor: '#000',
                legendFontSize: 15,
            },
        ],
        month: [
            {
                name: 'Consumo',
                value: 1200,
                color: 'rgba(255, 99, 132, 1)',
                legendFontColor: '#000',
                legendFontSize: 15,
            },
            {
                name: 'Prodotto',
                value: 1300,
                color: 'rgba(75, 192, 192, 1)',
                legendFontColor: '#000',
                legendFontSize: 15,
            },
        ],
        year: [
            {
                name: 'Consumo',
                value: 5000,
                color: 'rgba(255, 99, 132, 1)',
                legendFontColor: '#000',
                legendFontSize: 15,
            },
            {
                name: 'Prodotto',
                value: 6000,
                color: 'rgba(75, 192, 192, 1)',
                legendFontColor: '#000',
                legendFontSize: 15,
            },
        ],
    };

    return { lineChartData: lineChartData[time], pieChartData: pieChartData[time] };
};
