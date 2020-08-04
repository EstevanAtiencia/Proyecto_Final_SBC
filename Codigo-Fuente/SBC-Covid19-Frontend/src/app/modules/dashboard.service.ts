import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AreaComponent } from 'src/app/shared/widgets/area/area.component';
@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor( protected http: HttpClient) { }

 
  
  covid19Reports(){
   return this.http.get('http://localhost:9010/cases');
 }

 
 covid19Reports2(){
  return this.http.get('http://localhost:9010/europatotal');
}


getItaly() {
  return this.http.get('http://localhost:9010/Italy');
}

getFrance() {
  return this.http.get('http://localhost:9010/France');
}

getRussia() {
  return this.http.get('http://localhost:9010/Russia');
}

  users: any = [];

  bigChart() {


    return [
{
   name: 'Asia',
   data: [502, 635, 809, 947, 1402, 3634, 5268]
}
     ];
  }

  cards() {
    return [71, 78, 39, 66];
  }

  pieChart() {
    return [{
      name: 'Chrome',
      y: 61.41,
      sliced: true,
      selected: true
    }, {
      name: 'Internet Explorer',
      y: 11.84
    }, {
      name: 'Firefox',
      y: 10.85
    }, {
      name: 'Edge',
      y: 4.67
    }, {
      name: 'Safari',
      y: 4.18
    }, {
      name: 'Sogou Explorer',
      y: 1.64
    }, {
      name: 'Opera',
      y: 1.6
    }, {
      name: 'QQ',
      y: 1.2
    }, {
      name: 'Other',
      y: 2.61
    }];
  }
}
