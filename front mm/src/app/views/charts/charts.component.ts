import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { of } from 'rxjs';
import { switchMap, tap } from 'rxjs/operators';

@Component({
  selector: 'app-charts',
  templateUrl: './charts.component.html',
  styleUrls: ['./charts.component.scss']
})
export class ChartsComponent implements OnInit {
  postsCountArrayFormatted: string[] | undefined;

  constructor( private http: HttpClient,){};

  allaccountsData : any;
  dataArray:any;
  uniqueLocations!: string[] | undefined;
  rateArray!: number[] | undefined;
  commentsCountArray!:number[] | undefined;
  postsCountArray!: number[] | undefined;
  chartOptions: any = {};
  chartBarData: any = {};
  chartLineData: any = {};

  ngOnInit(): void {
    this.retrieveallaccounts()
      .pipe(
        switchMap((responseData: any) => {
          this.allaccountsData = responseData;
          console.log('all accounts data ', this.allaccountsData);

          // Assuming chartData returns an object with the required properties
          return this.chartData(this.allaccountsData);
        }),
        tap((chartData: any) => {
          this.postsCountArray = chartData.postsCountArray;
          this.rateArray = chartData.rateArray;
          this.uniqueLocations = chartData.uniqueLocations;
          this.commentsCountArray = chartData.commentsCountArray;
        })
      )
      .subscribe(
        () => {
          // The data is available here
          console.log(
            'uniqueLocations',
            this.uniqueLocations,
            'postsCountArray',
            this.postsCountArray,
            'rateArray',
            this.rateArray,
            'commentsCountArray',
            this.commentsCountArray
          );

          // Call the buildChartOptions function after data is available
          this.buildBarChartOptions();
          this.buildLineChartOptions() ;
        },
        (error: any) => {
          console.log(error);
        }
      );


  }


//retrieve all accounts
retrieveallaccounts() {
  const headers = new HttpHeaders({
    'Content-Type':  'application/json',
    'Authorization': 'Basic',
    credentials: 'include',
  });

  // Return the observable without subscribing here
  return this.http.get<any[]>('http://localhost:8082/ExamenBlanc/Account/retrieve-all-accounts', { headers });
}
  
//object Values To Array
  objectValuesToArray(obj: { [key: string]: any }): any[] {
    return Object.values(obj);
  }

  
chartData(Data: any) {
  console.log('Original Data:', Data);
  this.dataArray = this.objectValuesToArray(Data);
  console.log(' Data array :', this.dataArray);


  if (!Array.isArray(Data) || Data.length === 0) {
    console.error('Invalid or empty data array.');
    // Return an observable with default data
    return of({
      uniqueLocations: [],
      rateArray: [],
      commentsCountArray: [],
      postsCountArray: [],
    });
  }

  const locationCounts: { [location: string]: { posts_count: number; comments_count: number; rate: number } } = {};
    this.dataArray.forEach((element: { fields: { location: any; posts_count: any; comments_count: any } }) => {
      const location = element?.fields?.location ?? 'Unknown';
      const postsCount = element?.fields?.posts_count ?? 0;
      const commentsCount = element?.fields?.comments_count ?? 0;
  
      if (!locationCounts[location]) {
        locationCounts[location] = { posts_count: 0, comments_count: 0 , rate:0};
      }
      locationCounts[location].posts_count += postsCount;
      locationCounts[location].comments_count += commentsCount;
  
    });
  
    // Calculate rate for each location
    Object.keys(locationCounts).forEach(location => {
      const totalPosts = locationCounts[location].posts_count;
      const totalComments = locationCounts[location].comments_count;
  
      locationCounts[location].rate = totalPosts !== 0 ? totalComments / totalPosts : 0;
    });
  
    const uniqueLocations = Object.keys(locationCounts);
    const postsCountArray = uniqueLocations?.map(location => locationCounts[location].posts_count/1000);
    const commentsCountArray = uniqueLocations?.map(location => locationCounts[location].comments_count/1000);
    const rateArray = uniqueLocations?.map(location => locationCounts[location].rate);

  return of({
    uniqueLocations: uniqueLocations,
    postsCountArray: postsCountArray, 
    rateArray: rateArray, 
    commentsCountArray: commentsCountArray, 
  });
}
buildBarChartOptions(): void {
   this.chartBarData = {
    labels: this.uniqueLocations ,
  
    datasets: [
      // {
      //   label: 'Posts Count',
      //   yAxisID: 'commentsCountYAxis', 
      //   suffix: 'K', 
      //   backgroundColor: 'rgba(220, 220, 220, 0.2)',
      //   borderColor: 'rgba(75, 192, 192, 1)',
      //   borderWidth: 2,
      //   data: this.postsCountArray,
      // },
      // {
      //   label: 'Comments Count',
      //   yAxisID: 'commentsCountYAxis', 
      //   suffix: 'K', 
      //   backgroundColor: 'rgba(151, 187, 205, 0.2)',
      //   borderColor: 'rgba(151, 187, 205, 1)',
      //   borderWidth: 2,
      //   data: this.commentsCountArray,
      // },
      {
        label: 'Rate',
        yAxisID: 'rateYAxis',  
        yAxisLabel:'Rate',     
        // backgroundColor: 'rgba(255, 99, 132, 0.2)',
        // borderColor: 'rgba(255, 99, 132, 1)',
        backgroundColor: 'rgba(151, 187, 205, 0.2)',
         borderColor: 'rgba(151, 187, 205, 1)',
        borderWidth: 2,
        data: this.rateArray,
        config: {
          animated: false,
        },
      },
    ],
    options: {
      animation: {
        duration: 0,
      },
      responsive: true,
      responsiveAnimationDuration: 0,
      maintainAspectRatio: false,
    },
  };
 
}
  chartBarOptions = {
    maintainAspectRatio: false,
    
  };

  buildLineChartOptions() : void{
    

    this.chartLineData = {
      labels: this.uniqueLocations,    
      datasets: [
        {
          label: 'Posts Count',
          backgroundColor: 'rgba(220, 220, 220, 0.2)',
          borderColor: 'rgba(220, 220, 220, 1)',
          pointBackgroundColor: 'rgba(220, 220, 220, 1)',
          pointBorderColor: '#fff',
          data: this.postsCountArray,
          tooltip: {
            enabled: true,
            callbacks: {
              label: (tooltipItem: any) => {
               return tooltipItem.formattedValue +' (k)';
               },
              
            },

           
          },
        },
        {
          label: 'Comments Count',
          // backgroundColor: 'rgba(151, 187, 205, 0.2)',
          // borderColor: 'rgba(151, 187, 205, 1)',
          // pointBackgroundColor: 'rgba(151, 187, 205, 1)',
          backgroundColor: 'rgba(255, 99, 132, 0.2)',
          borderColor: 'rgba(255, 99, 132, 1)',
          pointBackgroundColor: 'rgba(255, 99, 132, 1)',
          pointBorderColor: '#fff',
          data:this.commentsCountArray,
          tooltip: {
            enabled: true,
            callbacks: {
              label: (tooltipItem: any) => {
               return tooltipItem.formattedValue +' (k)';
               },
              
            },
          },
        },
        // {
        //   label: 'Rate',
        //   backgroundColor: 'rgba(255, 99, 132, 0.2)',
        //   borderColor: 'rgba(255, 99, 132, 1)',
        //   pointBackgroundColor: 'rgba(255, 99, 132, 1)',
        //   pointBorderColor: '#fff',
        //   data: this.rateArray,
        // },
      ],
    
    };
  
    
  
  }
   chartLineOptions = {

    maintainAspectRatio: false,
    scales: {
      y: {
        beginAtZero: true,
        title: {
          display: true,
          text: 'Count (k)',
        },
        ticks: {
          callback: function(value: any, index: any, values: any) {
            return value + ' k';
          },
        },
      },
    },
    plugins: {
      tooltip: {
        callbacks: {
          label: function(context: { parsed: { y: number; }; }) {
            return context.parsed.y + 'k';
          },
        },
      },
    },
  };
  

  chartDoughnutData = {
    labels: ['VueJs', 'EmberJs', 'ReactJs', 'Angular'],
    datasets: [
      {
        backgroundColor: ['#41B883', '#E46651', '#00D8FF', '#DD1B16'],
        data: [40, 20, 80, 10]
      }
    ]
  };

  // chartDoughnutOptions = {
  //   aspectRatio: 1,
  //   responsive: true,
  //   maintainAspectRatio: false,
  //   radius: '100%'
  // };

  chartPieData = {
    labels: ['Red', 'Green', 'Yellow'],
    datasets: [
      {
        data: [300, 50, 100],
        backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56'],
        hoverBackgroundColor: ['#FF6384', '#36A2EB', '#FFCE56']
      }
    ]
  };

  // chartPieOptions = {
  //   aspectRatio: 1,
  //   responsive: true,
  //   maintainAspectRatio: false,
  //   radius: '100%'
  // };

  chartPolarAreaData = {
    labels: ['Red', 'Green', 'Yellow', 'Grey', 'Blue'],
    datasets: [
      {
        data: [11, 16, 7, 3, 14],
        backgroundColor: ['#FF6384', '#4BC0C0', '#FFCE56', '#E7E9ED', '#36A2EB']
      }
    ]
  };

  chartRadarData = {
    labels: ['Eating', 'Drinking', 'Sleeping', 'Designing', 'Coding', 'Cycling', 'Running'],
    datasets: [
      {
        label: '2020',
        backgroundColor: 'rgba(179,181,198,0.2)',
        borderColor: 'rgba(179,181,198,1)',
        pointBackgroundColor: 'rgba(179,181,198,1)',
        pointBorderColor: '#fff',
        pointHoverBackgroundColor: '#fff',
        pointHoverBorderColor: 'rgba(179,181,198,1)',
        tooltipLabelColor: 'rgba(179,181,198,1)',
        data: [65, 59, 90, 81, 56, 55, 40]
      },
      {
        label: '2021',
        backgroundColor: 'rgba(255,99,132,0.2)',
        borderColor: 'rgba(255,99,132,1)',
        pointBackgroundColor: 'rgba(255,99,132,1)',
        pointBorderColor: '#fff',
        pointHoverBackgroundColor: '#fff',
        pointHoverBorderColor: 'rgba(255,99,132,1)',
        tooltipLabelColor: 'rgba(255,99,132,1)',
        data: [this.randomData, this.randomData, this.randomData, this.randomData, this.randomData, this.randomData, this.randomData]
      }
    ]
  };

  // chartRadarOptions = {
  //   aspectRatio: 1.5,
  //   responsive: true,
  //   maintainAspectRatio: false,
  // };

  get randomData() {
    return Math.round(Math.random() * 100);
  }

}
