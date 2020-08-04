import { Component, OnInit, Input } from '@angular/core';
import * as Highcharts from 'highcharts';
import HC_exporting from 'highcharts/modules/exporting';
import { DashboardService } from 'src/app/modules/dashboard.service';

@Component({
  selector: 'app-widget-pie',
  templateUrl: './pie.component.html',
  styleUrls: ['./pie.component.scss']
})
export class PieComponent implements OnInit {

 // Highcharts = Highcharts;
  chartOptions = {};

  @Input() data = [];
  users: any  =[]
  Highcharts: typeof Highcharts = Highcharts;

  
  graficaPrueba1:Highcharts.Options = {
    chart: {
      zoomType: 'xy'
  },
  title: {
      text: 'Número de pruebas covid19'
  },
  subtitle: {
      text: 'Paises con mayor número de pruebas'
  },
  xAxis: [{
      categories: [],
      crosshair: true
  }],
  yAxis: [{ // Primary yAxis
      /*labels: {
          format: '{value}°C',
          style: {
              color: Highcharts.getOptions().colors[1]
          }
      },*/
     /* title: {
          text: 'Temperature',
          style: {
              color: Highcharts.getOptions().colors[1]
          }
      }*/
  }, { // Secondary yAxis
     /* title: {
          text: 'Rainfall',
          style: {
              color: Highcharts.getOptions().colors[0]
          }
      },*/
      labels: {
          format: '{value} pruebas',
          style: {
              color: Highcharts.getOptions().colors[0]
          }
      },
      opposite: true
  }],
  tooltip: {
      shared: true
  },
  legend: {
      layout: 'vertical',
      align: 'left',
      x: 120,
      verticalAlign: 'top',
      y: 100,
      floating: true,
      backgroundColor:
          Highcharts.defaultOptions.legend.backgroundColor || // theme
          'rgba(255,255,255,0.25)'
  },
  series: [{
      name: 'Pruebas',
      type: 'column',
      yAxis: 1,
      data: [],
      tooltip: {
          valueSuffix: ' '
      }

  }, /*{
      name: 'Temperature',
      type: 'spline',
      data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6],
      tooltip: {
          valueSuffix: '°C'
      }
  }*/]
}
  constructor( protected service:DashboardService ) { }

  ngOnInit() {
  
this.service.covid19Reports()
.subscribe(
  (data) => { // Success
    this.users = data;
    const datosGrafica = this.users.map(x => x.tests);
    const nombre = this.users.map(x  => x.country);

    //Highcharts
    this.graficaPrueba1.series[0]['data'] = datosGrafica;
    this.graficaPrueba1.xAxis['categories'] = nombre;
    Highcharts.chart('MediosdPPrincipal1', this.graficaPrueba1);

  },
  (err) => {
    console.error(err);
  }
);
    HC_exporting(Highcharts);

    setTimeout(() => {
      window.dispatchEvent(
        new Event('resize')
      );
    }, 300);
  }

}
