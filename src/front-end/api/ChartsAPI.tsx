type TimeType = 'giorno' | 'mese' | 'anno';

const ChartsAPI = {
    fetchLineChartData: (time: TimeType) => {
        const charts = {
            giorno: {
                lineChart: 'line_day.png',
                pieChart: 'pie_day.png',
            },
            mese: {
                lineChart: 'line_month.png',
                pieChart: 'pie_month.png',
            },
            anno: {
                lineChart: 'line_year.png',
                pieChart: 'pie_year.png',
            },
        };

        // Restituisce i grafici corrispondenti
        return charts[time] || charts['giorno'];
    },
};

export default ChartsAPI;
