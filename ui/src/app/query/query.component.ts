import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Observable } from 'rxjs';
import { RestFacadeService } from "../service/rest-facade.service";
import { TableInfoDTO } from "../ext";
import Chart from 'chart.js/auto';
import { ChartType } from 'chart.js';

@Component({
  selector: 'app-query',
  templateUrl: './query.component.html',
  styleUrls: ['./query.component.css']
})
export class QueryComponent implements OnInit {

  @ViewChild('myChart') myChart: ElementRef<HTMLCanvasElement> | null = null;
  public selectedTable: string = '';
  public selectedColumn1: string = '';
  public selectedColumn2: string = '';
  fieldNames: string[] = [];
  chart: Chart | null = null;
  public selectedChartType: ChartType = 'bar';
  public chartTypes: ChartType[] = ['bar', 'line', 'pie', 'radar', 'scatter', 'bubble', 'polarArea'];
  public filterOptions: string[] = ['none', 'sum', 'count', 'mean'];
  public selectedFilter1: string = 'none';
  public selectedFilter2: string = 'none';
  public filterType: 'none' | 'greaterThan' | 'lessThan' = 'none';
  public filterValue: number = 0;
  public numberValue: number | undefined;

  constructor(private restFacadeService: RestFacadeService) {}

  ngOnInit(): void {}

  getTableInfo() {
    switch(this.selectedTable) {
      case 'sales':
        this.fetchTableInfo(() => this.restFacadeService.getTableInfoSales());
        break;
      case 'listing':
        this.fetchTableInfo(() => this.restFacadeService.getTableInfoListing());
        break;
      case 'user':
        this.fetchTableInfo(() => this.restFacadeService.getTableInfoUser());
        break;
      case 'venue':
        this.fetchTableInfo(() => this.restFacadeService.getTableInfoVenue());
        break;
      case 'event':
        this.fetchTableInfo(() => this.restFacadeService.getTableInfoEvent());
        break;
      case 'category':
        this.fetchTableInfo(() => this.restFacadeService.getTableInfoCategory());
        break;
      case 'date':
        this.fetchTableInfo(() => this.restFacadeService.getTableInfoDate());
        break;

    }
  }

  onColumnSelect() {
    this.queryDataAndUpdateChart();
  }

  onDisplayChart(limit?: number) {
    this.queryDataAndUpdateChart(limit);
  }

  private fetchTableInfo(fetchFunction: () => Observable<TableInfoDTO>) {
    fetchFunction().subscribe({
      next: res => {
        if (res.schema?.fields) {
          this.fieldNames = res.schema.fields
            .map(field => field.name)
            .filter((name): name is string => name !== undefined);
        }
      },
      error: err => console.log('Failed', err)
    })
  }

  /*
  private queryDataAndUpdateChart() {
      switch(this.selectedTable) {
        case 'sales':
          this.restFacadeService.queryAllSales().subscribe(data => {
            this.updateChartData(data);
          });
          break;
        case 'listing':
          this.restFacadeService.queryAllListing().subscribe(data => {
            this.updateChartData(data);
          });
          break;
        case 'user':
          this.restFacadeService.queryAllUser().subscribe(data => {
            this.updateChartData(data);
          });
          break;
        case 'venue':
          this.restFacadeService.queryAllVenue().subscribe(data => {
            this.updateChartData(data);
          });
          break;
        case 'event':
          this.restFacadeService.queryAllEvent().subscribe(data => {
            this.updateChartData(data);
          });
          break;
        case 'category':
          this.restFacadeService.queryAllCategory().subscribe(data => {
            this.updateChartData(data);
          });
          break;
        case 'date':
          this.restFacadeService.queryAllDate().subscribe(data => {
            this.updateChartData(data);
          });
          break;
      }
    }
   */

  private queryDataAndUpdateChart(limit?: number) {
    switch(this.selectedTable) {
      case 'sales':
        this.restFacadeService.queryFirstSales(limit).subscribe(data => {
          this.updateChartData(data);
        });
        break;
      case 'listing':
        this.restFacadeService.queryFirstListing(limit).subscribe(data => {
          this.updateChartData(data);
        });
        break;
      case 'user':
        this.restFacadeService.queryFirstUser(limit).subscribe(data => {
          this.updateChartData(data);
        });
        break;
      case 'venue':
        this.restFacadeService.queryFirstVenue(limit).subscribe(data => {
          this.updateChartData(data);
        });
        break;
      case 'event':
        this.restFacadeService.queryFirstEvent(limit).subscribe(data => {
          this.updateChartData(data);
        });
        break;
      case 'category':
        this.restFacadeService.queryFirstCategory(limit).subscribe(data => {
          this.updateChartData(data);
        });
        break;
      case 'date':
        this.restFacadeService.queryFirstDate(limit).subscribe(data => {
          this.updateChartData(data);
        });
        break;
    }
  }
  private applyFilter(data: any[], column: string, filter: string): any[] {
    switch (filter) {
      case 'sum':
        return [data.reduce((acc, item) => acc + (item[column] || 0), 0)];
      case 'count':
        return [data.length];
      case 'mean':
        return [data.reduce((acc, item) => acc + (item[column] || 0), 0) / data.length];
      default:
        return data.map(item => item[column]);
    }
  }

  private updateChartData(data: any[]) {
    // Appliquer les filtres sélectionnés
    const filteredData1 = this.applyFilter(data, this.selectedColumn1, this.selectedFilter1);
    const filteredData2 = this.applyFilter(data, this.selectedColumn2, this.selectedFilter2);

    // Filtrer les données en fonction du filtre de seuil
    let finalFilteredData = data;
    if (this.filterType === 'greaterThan') {
      finalFilteredData = data.filter(item => item[this.selectedColumn1] > this.filterValue);
    } else if (this.filterType === 'lessThan') {
      finalFilteredData = data.filter(item => item[this.selectedColumn1] < this.filterValue);
    }

    // Tri des données par la colonne sélectionnée pour l'axe X
    finalFilteredData.sort((a, b) => {
      if (a[this.selectedColumn2] < b[this.selectedColumn2]) {
        return -1;
      }
      if (a[this.selectedColumn2] > b[this.selectedColumn2]) {
        return 1;
      }
      return 0;
    });

    // Préparation des données pour le graphique
    const labels = finalFilteredData.map(item => item[this.selectedColumn2]);
    const values = finalFilteredData.map(item => item[this.selectedColumn1]);

    if (this.chart) {
      this.chart.destroy();
    }

    if (this.myChart && this.myChart.nativeElement) {
      this.chart = new Chart(this.myChart.nativeElement, {
        type: this.selectedChartType,
        data: {
          labels: labels,
          datasets: [{
            label: `${this.selectedColumn1} by ${this.selectedColumn2}`,
            data: values,
            backgroundColor: 'rgba(0, 123, 255, 0.5)',
            borderColor: 'rgba(0, 123, 255, 1)',
            borderWidth: 1
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          scales: {
            y: {
              beginAtZero: true
            }
          }
        }
      });
    } else {
      console.error('ElementRef myChart is null');
    }
  }




}
