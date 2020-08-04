import { Component, OnInit, Input } from '@angular/core';
import * as Highcharts from 'highcharts';
import HC_exporting from 'highcharts/modules/exporting';
import { DashboardService } from 'src/app/modules/dashboard.service';

@Component({
  selector: 'app-widget-area1',
  templateUrl: './area1.component.html',
  styleUrls: ['./area1.component.scss']
})
export class Area1Component implements OnInit {
  chartOptions: {};
  @Input() data: any = [];

  users: any = []
  Highcharts: typeof Highcharts = Highcharts;
  // Highcharts = Highcharts;
  graficaPrueba: Highcharts.Options = {
    chart: {
      type: 'area'
    },
    title: {
      text: 'Países con más casos'
    },
    subtitle: {
      text: 'de Covid-19'
    },
    tooltip: {
      split: true,
      valueSuffix: ' cases'
    },
    credits: {
      enabled: false
    },
    exporting: {
      enabled: true,
    },
    xAxis: {
      categories: []
    },
    plotOptions: {
      area: {
        stacking: 'normal',
        lineColor: '#666666',
        lineWidth: 1,
        marker: {
          lineWidth: 1,
          lineColor: '#666666'
        }
      }
    },
    series: [
      {
        name: 'Italy',
        data: [],
        type: 'area'
      }, {
        name: 'France',
        data: [],
        type: 'area'
      }, {
        name: 'Russia',
        data: [],
        type: 'area'
      }

    ]

  };

  constructor(protected service: DashboardService) { }

  ngOnInit() {
    this.service.getItaly()
      .subscribe(
        (data) => { // Success
          this.users = data;
          const datosGrafica = this.users.map(x => Number(x.casos));
          const nombre = this.users.map(x => x.fecha);

          //Highcharts
          this.graficaPrueba.series[0]['data'] = datosGrafica;
          this.graficaPrueba.xAxis['categories'] = nombre;
          Highcharts.chart('MediosdPPrincipal2', this.graficaPrueba);

        },
        (err) => {
          console.error(err);
        }
      );


    this.service.getFrance()
      .subscribe(
        (data) => { // Success
          this.users = data;
          const datosGrafica = this.users.map(x => Number(x.casos));
          const nombre = this.users.map(x => x.fecha);

          //Highcharts
          this.graficaPrueba.series[1]['data'] = datosGrafica;
          this.graficaPrueba.xAxis['categories'] = nombre;
          Highcharts.chart('MediosdPPrincipal2', this.graficaPrueba);

        },
        (err) => {
          console.error(err);
        }
      );

    this.service.getRussia()
      .subscribe(
        (data) => { // Success
          this.users = data;
          const datosGrafica = this.users.map(x => Number(x.casos));
          const nombre = this.users.map(x => x.fecha);

          //Highcharts
          this.graficaPrueba.series[2]['data'] = datosGrafica;
          this.graficaPrueba.xAxis['categories'] = nombre;
          Highcharts.chart('MediosdPPrincipal2', this.graficaPrueba);

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
