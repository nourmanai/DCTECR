import {  Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { cilUserFemale,cibCampaignMonitor, cibFacebook, cibInstagram, cibLinkedin, cibTwitter, cibYoutube, cifAd, cifAe, cifAf, cifAg, cifAl, cifAm, cifAo, cifAr, cifAt, cifAu, cifAz, cifBa, cifBb, cifBd, cifBe, cifBf, cifBg, cifBh, cifBi, cifBj, cifBn, cifBo, cifBr, cifBs, cifBt, cifBw, cifBy, cifBz, cifCa, cifCd, cifCf, cifCg, cifCh, cifCi, cifCl, cifCm, cifCn, cifCo, cifCr, cifCu, cifCv, cifCy, cifCz, cifDe, cifDj, cifDk, cifDm, cifDo, cifDz, cifEc, cifEe, cifEg, cifEr, cifEs, cifEt, cifFi, cifFj, cifFm, cifFr, cifGa, cifGb, cifGd, cifGe, cifGh, cifGm, cifGn, cifGq, cifGr, cifGt, cifGw, cifGy, cifHk, cifHn, cifHr, cifHt, cifHu, cifId, cifIe, cifIl, cifIn, cifIq, cifIr, cifIs, cifIt, cifJm, cifJo, cifJp, cifKe, cifKg, cifKh, cifKi, cifKm, cifKn, cifKp, cifKr, cifKw, cifKz, cifLa, cifLb, cifLc, cifLi, cifLk, cifLr, cifLs, cifLt, cifLu, cifLv, cifLy, cifMa, cifMc, cifMd, cifMe, cifMg, cifMh, cifMk, cifMl, cifMm, cifMn, cifMr, cifMt, cifMu, cifMv, cifMw, cifMx, cifMy, cifMz, cifNa, cifNe, cifNg, cifNi, cifNl, cifNo, cifNp, cifNr, cifNu, cifNz, cifOm, cifPa, cifPe, cifPg, cifPh, cifPk, cifPl, cifPt, cifPw, cifPy, cifQa, cifRo, cifRs, cifRu, cifRw, cifSa, cifSb, cifSc, cifSd, cifSe, cifSg, cifSi, cifSk, cifSl, cifSm, cifSn, cifSo, cifSr, cifSs, cifSt, cifSv, cifSy, cifSz, cifTd, cifTg, cifTh, cifTj, cifTl, cifTm, cifTn, cifTo, cifTr, cifTt, cifTv, cifTw, cifTz, cifUa, cifUg, cifUs, cifUy, cifUz, cifVa, cifVc, cifVe, cifVn, cifWs, cifXk, cifYe, cifZa, cifZm, cifZw, cilArrowBottom, cilArrowThickBottom, cilArrowTop, cilBellExclamation, cilBookmark, cilCalendar, cilCalendarCheck, cilCart, cilCash, cilChartPie, cilCheckCircle, cilFilterX, cilFolderOpen, cilFrown, cilGlobeAlt, cilHandshake, cilHappy, cilHeart, cilList, cilListFilter, cilListNumbered, cilMediaRecord, cilMeh, cilMinus, cilMonitor, cilOptions, cilPaperPlane, cilPuzzle, cilReload, cilSearch, cilStar, cilTag, cilThumbDown, cilThumbUp, cilUser, cilWallet, cilWarning } from '@coreui/icons';
import { IconSetService } from '@coreui/icons-angular';
import { GlobalService } from '../GlobalService';
import { Chart, ChartData, ChartOptions, LegendItem,ScatterDataPoint, TooltipItem  } from 'chart.js';
import * as am5 from "@amcharts/amcharts5";
import * as am5xy from "@amcharts/amcharts5/xy";
import * as am5radar from "@amcharts/amcharts5/radar";
import am5themes_Animated from "@amcharts/amcharts5/themes/Animated";
@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrl: './clients.component.scss'
})
export class ClientsComponent implements OnInit {
  allClientsData: any[]=[];
  initialClientsData: any[]=[];
  itemsPerPageTeam = 5; 
  itemsPerPage = 5; 
  currentPage = 1;
  currentPageTeam = 1;
  clickedClient:any;
  activeLinkIndex: number = -1;
  showClientFilters: boolean = false;
  genderFilter: any = '';
  satisfactionFilter: any =null;
  ressellerFilter: any =null;
  segmentFilter: any ='';
  UniqueGenders: any[]=[];
  countGender : { [key: string]: number }={}; 
  Genderlabels: string[]=[];
  Genderdata: number[]=[];
  GenderchartPieData:any;
  countreseller: { [key: string]: number; } ={};
  ReselerchartPieData: any;
  uniqueValue: any[]=[];
  countSegment: { [key: string]: number; } ={};
  SegmentchartPieData:any;
  Uniquenbr_purchase_Monthly: any[] =[];
  countnbr_purchase_Monthly: { [key: string]: number; } ={};
  nbr_purchase_Monthly_chartBarData:any;
  chartLinenbr_purchase_Monthly_Data:any;
  Smile:any;
  BudgetGaugechart: any  = null;
  satisfiedgaugeValue: number =0;
  unsatisfiedgaugeValue: number =0;
  scatterChartData: ChartData<'scatter', ScatterDataPoint[]>={
    datasets: []
  };
  countage: { [key: string]: number; } = {};
  ageSegmentationData: any;
  countpurchases: { [key: string]: number; } ={};
  countgenderpurchases: { [key: string]: number; } ={};
  countsegmentpurchases: { [key: string]: number; }={};
  genderpurchaseschartPieData: any;
  segmentpurchaseschartPieData: any;
  expandedClient: any;
  searchText: any='';

  constructor(public domSanitizer: DomSanitizer, public globalService: GlobalService , public iconSet: IconSetService, )
  {
   iconSet.icons = {cibTwitter,cibLinkedin,cibYoutube,cibFacebook,cilUserFemale,cilFolderOpen, cibCampaignMonitor ,cibInstagram,cilCash,cilFilterX,cilList,cilListFilter,cilTag,cilPuzzle,cilCart,cilWallet,cilStar,cilThumbUp,cilThumbDown,cilHeart,cilHandshake,cilSearch,cilFrown,cilHappy,cilMeh,cilArrowThickBottom,cilMediaRecord,cilReload,cilArrowBottom,cilArrowTop,cilMinus,cilMonitor,cilChartPie,cilOptions,cilCheckCircle, cilGlobeAlt, cilListNumbered, cilPaperPlane, cilBellExclamation, cilBookmark, cilCalendar , cilCalendarCheck, cilUser, cilWarning, cifUs, cifAd, cifBr, cifBs, cifBa, cifCa, cifBb,cifCd, cifAe,cifCh, cifCi, cifBj, cifCl,cifCm,cifCo,cifCr,cifAt,cifCu,cifCv, cifBw,cifCy,cifCz,cifDe,cifDj,cifDk,cifDm,cifDo,cifDz,cifEc,cifEe,cifEg,cifEr,cifEs,cifEt,cifFi,cifFj,cifFm,cifFr,cifId,cifIe,cifIl,cifIn,cifIq,cifIr,cifIs,cifIt,cifJm,cifJo,cifJp,cifKe,cifKg,cifKh,cifKi,cifKm,cifKn,cifKp,cifKr,cifKw,cifKz,cifGa,cifGb,cifGd,cifGe,cifGh,cifGm,cifGn,cifGq,cifGr,cifGt,cifGw,cifGy,cifHk,cifHn,cifHr,cifHt,cifHu,cifLa,cifLb,cifLc,cifLi,cifLk,cifLr,cifLs,cifLt, cifLu,cifLv,cifLy,cifMa,cifMc,cifMd,cifMe,cifMg,cifMh,cifMk,cifMl,cifMm,cifMn,cifMr,cifMt,cifMu,cifMv,cifMw,cifMx,cifMy,cifMz,cifNa,cifNe,cifNg,cifNi,cifNl,cifNo,cifNp,cifNr,cifNu,cifNz,cifOm,cifPa,cifPe,cifPg,cifPh,cifPk,cifPl,cifPt,cifPw,cifPy,cifQa,cifRo,cifRs,cifRu,cifRw,cifSa,cifSb,cifSc,cifSd,cifSe,cifSg,cifSi,cifSk,cifSl,cifSm,cifSn,cifSo,cifSr,cifSs,cifSt,cifSv,cifSy,cifSz,cifTd,cifTg,cifTh,cifTj,cifTl,cifTm,cifTn,cifTo,cifTr,cifTt,cifTv,cifTw,cifTz,cifUa,cifUg,cifUy,cifUz,cifVa,cifVc,cifVe,cifVn,cifWs,cifXk,cifYe,cifZa,cifZm,cifZw,cifAf,cifAg,cifAl,cifAm,cifAo,cifAr,cifAu,cifAz,cifBd,cifBe,cifBf,cifBg,cifBh,cifBi,cifBn,cifBo,cifBt,cifBy,cifBz,cifCf,cifCg,cifCn  }; 

     
 }


  ngOnInit(): void {

   // Clients 
   this.globalService.retrieveall('Clients','Clients').subscribe((responseData: any) => {
    
     this.allClientsData = responseData;
     this.initialClientsData = responseData;
     this.UniqueGenders = this.globalService.getUniqueOptions(this.allClientsData,'','gender');
     this.countGender = this.countClientsByField('gender');
     this.countreseller = this.countClientsByField('reseller'); 
     this.countage= this.countClientsByField('age');
     this.uniqueValue = this.globalService.getUniqueOptions(this.allClientsData,'','Segment');
     this.countSegment = this.countClientsByField('Segment'); 
     this.Uniquenbr_purchase_Monthly = this.globalService.getUniqueOptions(this.allClientsData,'','nbr_purchase_Monthly');
     this.countnbr_purchase_Monthly = this.countClientsByField('nbr_purchase_Monthly');
     this.countpurchases= this.countClientsByField('purchases');
     this.countgenderpurchases= this.countClientsByField('genderpurchases');
     this.countsegmentpurchases= this.countClientsByField('segmentpurchases');     
     const colorMap = this.globalService.getConsistentColorMap(Object.values(this.countnbr_purchase_Monthly));
     const colors = Object.values(this.countnbr_purchase_Monthly).map(nbr => colorMap[nbr]);


    this.GenderchartPieData = {
      labels:Object.keys(this.countGender),
      datasets: [
        {
          data: Object.values(this.countGender),
          backgroundColor: ['#FF6384', '#36A2EB'],
          hoverBackgroundColor: ['#FF6384', '#36A2EB'],
          tooltip: {
            enabled: true,
            callbacks: {
              label: (tooltipItem: any) => {
               return tooltipItem.label+' : ' +tooltipItem.formattedValue+' = '+(tooltipItem.formattedValue *100 /this.allClientsData.length).toFixed(2)  +' %';
               },
              
            },
          },
        }
      ]
    };
    this.ReselerchartPieData = {
      labels:Object.keys(this.countreseller),
      datasets: [
        {
          data: Object.values(this.countreseller),
          backgroundColor: ['#D3D3D3', '#5EC961'],
          hoverBackgroundColor: ['#C0C0C0', '#28a745'],
          tooltip: {
            enabled: true,
            callbacks: {
              label: (tooltipItem: any) => {
              return tooltipItem.label+' : ' +tooltipItem.formattedValue+' = ' +(tooltipItem.formattedValue *100 /this.allClientsData.length).toFixed(2)  +' %'; }

            },
          },
     
        },
        
      ]
    };
    this.SegmentchartPieData = {
      labels:Object.keys(this.countSegment),
      datasets: [
        {
          data: Object.values(this.countSegment),
          backgroundColor: ['#e45665', '#5cb85c', '#ffd166'],
          hoverBackgroundColor: ['#dc3545', '#28a745', '#ffc107'],
          tooltip: {
            enabled: true,
            callbacks: {
              label: (tooltipItem: any) => {
              return tooltipItem.label+' : ' +tooltipItem.formattedValue+' = ' +(tooltipItem.formattedValue *100 /this.allClientsData.length).toFixed(2)  +' %'; }

            },
          },
     
        },
        
      ]
    };
    this.genderpurchaseschartPieData = {
      labels:Object.keys(this.countgenderpurchases),
      datasets: [
        {
          data: Object.values(this.countgenderpurchases),
          backgroundColor: ['#FF6384', '#36A2EB'],
          hoverBackgroundColor: ['#FF6384', '#36A2EB'],
          tooltip: {
            enabled: true,
            callbacks: {
              label: (tooltipItem: any) => {
              return tooltipItem.label+' : ' +tooltipItem.formattedValue+' purchases'; }

            },
          },
     
        },
        
      ]
    };
    this.segmentpurchaseschartPieData = {
      labels:Object.keys(this.countsegmentpurchases),
      datasets: [
        {
          data: Object.values(this.countsegmentpurchases),
          backgroundColor: ['#e45665', '#5cb85c', '#ffd166'],
          hoverBackgroundColor: ['#dc3545', '#28a745', '#ffc107'],
          tooltip: {
            enabled: true,
            callbacks: {
              label: (tooltipItem: any) => {
              return tooltipItem.label+' : ' +tooltipItem.formattedValue+' purchases'; }

            },
          },
     
        },
        
      ]
    };
    this.Scatterchart(this.allClientsData);

    this.nbr_purchase_Monthly_chartBarData = {
      labels: Object.keys(this.countnbr_purchase_Monthly),
    
      datasets: [

        {
          label: 'nbr clients',
          yAxisLabel:'nbr clients',  
          xAxisLabel:'nbr Monthly purchases ',        
          backgroundColor: colors,
           borderColor: colors.map((color: string) => color.replace('0.2', '1')),
          borderWidth: 2,
          data: Object.values(this.countnbr_purchase_Monthly),
          config: {
            animated: false,
          },
        },
      ],
    
    };

    this.ageSegmentationData = {
      labels:Object.keys(this.countage),
      datasets: [
        {
          label: 'Number of Clients',
          data: Object.values(this.countage),
          backgroundColor:' rgba(173, 216, 230, 0.8)',
          borderColor: 'rgba(173, 216, 230, 0.9)',
          borderWidth: 1,
        },
        {
          label: 'Number of purchases',
          data: Object.values(this.countpurchases),
          backgroundColor: 'rgba(216, 191, 216, 0.8)',
          borderColor: 'rgba(216, 191, 216, 0.9)',
          borderWidth: 1,
        },
      ],
    };
  
   
   
    // this.chartLinenbr_purchase_Monthly_Data = {
    //   labels: Object.keys(this.countnbr_purchase_Monthly),    
    //   datasets: [
    //     {
    //       label: 'Shipping Types Count',
    //       backgroundColor: 'rgba(220, 220, 220, 0.2)',
    //       borderColor: 'rgba(220, 220, 220, 1)',
    //       pointBackgroundColor: 'rgba(255, 99, 132, 1)',
    //       pointBorderColor: '#fff',
    //       data: Object.values(this.countnbr_purchase_Monthly),
    //       tooltip: {
    //         enabled: true,
    //         callbacks: {
    //           label: (tooltipItem: any) => {
    //            return tooltipItem.formattedValue;
    //            },
              
    //         },      
    //       },
    //     }
    //   ],
    
    // };
   
    let satisfiedCount = this.countClientsByField('satisfied')['satisfied'];
    let notSatisfiedCount = this.countClientsByField('satisfied')['not satisfied'];
  
    const totalClients = satisfiedCount + notSatisfiedCount;
    if (totalClients > 0) {
      this.satisfiedgaugeValue = (satisfiedCount / totalClients) * 100; 
      this.unsatisfiedgaugeValue = (notSatisfiedCount / totalClients) * 100; 

    }
  
    // Determine smile icon based on gauge value
    if ( this.satisfiedgaugeValue < 40) {
      this.Smile = 'cilFrown';
    } else if ( this.satisfiedgaugeValue >= 40 &&  this.satisfiedgaugeValue <= 60) {
      this.Smile = 'cilMeh';
    } else {
      this.Smile = 'cilHappy';
    }

  
    setTimeout(() => {
      this.GaugeChart([ this.satisfiedgaugeValue], 'satisfactionchartdiv', 'satisfied');
    }, 0);


     console.log(" all Clients Data",this.allClientsData );
     console.log(" UniqueGenders",this.UniqueGenders );
     console.log(" countGender",this.countGender );
     console.log(" countreseller",this.countreseller );
     console.log("countage", this.countage);
     console.log("countsegmentpurchases", this.countsegmentpurchases);
     console.log("countgenderpurchases", this.countgenderpurchases);





   },
   (error: any) => {
     console.log(error);
   }
   );
  }




// GaugeChart

  disposeChart(chart: any): void {
    if (chart) {
      chart.dispose();
      chart = null;
    }
  }
  GaugeChart(data: any, divname: string, chartType: string): any {
    let chartRoot: any;
  
    if (chartType === "satisfied") {
      this.disposeChart(this.BudgetGaugechart);
      chartRoot = am5.Root.new(divname);
      this.BudgetGaugechart = chartRoot;}

    console.log("gauge data ", data);
    let currentIndex = 0;
  
    chartRoot.setThemes([am5themes_Animated.new(chartRoot)]);
  
    // Create chart
    let chart = chartRoot.container.children.push(
      am5radar.RadarChart.new(chartRoot, {
        panX: false,
        panY: false,
        startAngle: 180,
        endAngle: 360,
        width: 300,
        height: 300,
      })
    );
  
    let axisRenderer = am5radar.AxisRendererCircular.new(chartRoot, {
      innerRadius: -10,
      strokeOpacity: 1,
      strokeWidth: 15,
      strokeGradient: am5.LinearGradient.new(chartRoot, {
        rotation: 0,
        stops: [
          { color: am5.color(0xfb7116) },
          { color: am5.color(0xf6d32b) },
          { color: am5.color(0xf4fb16) },
          { color: am5.color(0x19d228) }
        ]
      })
    });
  
    let xAxis = chart.xAxes.push(
      am5xy.ValueAxis.new(chartRoot, {
        maxDeviation: 0,
        min: 0,
        max: 100,
        strictMinMax: true,
        renderer: axisRenderer
      })
    );
  
    let axisDataItem = xAxis.makeDataItem({});
    axisDataItem.set("value", 0);
  
    let bullet = axisDataItem.set("bullet", am5xy.AxisBullet.new(chartRoot, {
      sprite: am5radar.ClockHand.new(chartRoot, {
        radius: am5.percent(99)
      })
    }));
  
    xAxis.createAxisRange(axisDataItem);
  
    axisDataItem.get("grid")?.set("visible", false);
  
    const updateChartWithData = () => {
      if (currentIndex < data.length) {
        let value = data[currentIndex];
        axisDataItem.animate({
          key: "value",
          to: value,
          duration: 800,
          easing: am5.ease.out(am5.ease.cubic)
        });
        currentIndex++;
      }
    };
  
    updateChartWithData(); 
  
    if (data.length > 1) {
      let updateInterval = setInterval(() => {
        if (currentIndex >= data.length) {
          clearInterval(updateInterval);
        } else {
          updateChartWithData();
        }
      }, 2000);
    }
  
    chart.appear(1000, 100);
  
    return chartRoot;
  }

// Scatterchart
  Scatterchart(clientData:any[]){
   
    let root = am5.Root.new("Scatterchartdiv");

    root.setThemes([
      am5themes_Animated.new(root)
    ]);

    let chart = root.container.children.push(am5radar.RadarChart.new(root, {
      panX: false,
      panY: false,
      wheelX: "panX",
      wheelY: "zoomX",
      innerRadius: am5.percent(20),
      layout: root.verticalLayout
    }));

    let cursor = chart.set("cursor", am5radar.RadarCursor.new(root, {
      behavior: "zoomX"
    }));

    cursor.lineY.set("visible", false);

    let xRenderer = am5radar.AxisRendererCircular.new(root, {
      strokeOpacity: 0.1
    });

    xRenderer.labels.template.setAll({
      radius: 10,
      maxPosition: 0.98,
      text: "{valueX} (k$)"
    });

    let xAxis = chart.xAxes.push(am5xy.ValueAxis.new(root, {
      renderer: xRenderer,
      extraMax: 0.1,
      tooltip: am5.Tooltip.new(root, {}),
    

    }));

    let yRenderer = am5radar.AxisRendererRadial.new(root, {});
    yRenderer.labels.template.setAll({
      text: "{valueY} (k$)"
    });

    let yAxis = chart.yAxes.push(am5xy.ValueAxis.new(root, {
      renderer:yRenderer,
    
    }));

    this.createSeries(chart, root, xAxis, yAxis, "credit card debt (k$)", clientData.map(client => ({ x: client.fields['annual Salary'].toFixed(0)/1000, y: client.fields['credit card debt'].toFixed(0)/1000 })));
    this.createSeries(chart, root, xAxis, yAxis, "net worth (k$)", clientData.map(client => ({ x: client.fields['annual Salary'].toFixed(0)/1000, y: client.fields['net worth'].toFixed(0)/1000 })));

    let legend = chart.children.push(am5.Legend.new(root, {
      x: am5.p50,
      centerX: am5.p50
    }));
    legend.data.setAll(chart.series.values);

    chart.appear(1000, 100);
  }

  createSeries(chart: am5radar.RadarChart, root: am5.Root, xAxis: am5xy.ValueAxis<am5xy.AxisRenderer>, yAxis: am5xy.ValueAxis<am5xy.AxisRenderer>, name: string, data: { x: number, y: number }[]) {
    let series = chart.series.push(am5radar.RadarLineSeries.new(root, {
      name: name,
      xAxis: xAxis,
      yAxis: yAxis,
      valueXField: "x",
      valueYField: "y",
      sequencedInterpolation: true
    }));

    series.set("stroke", root.interfaceColors.get("background"));
    series.strokes.template.setAll({
      forceHidden: true
    });

    series.bullets.push(function() {
      return am5.Bullet.new(root, {
        sprite: am5.Circle.new(root, {
          radius: 5,
          fill: series.get("fill")
        })
      });
    });

    series.data.setAll(data);
    series.appear(1000);
  
  }

  // Client Filter 

  toggleClientFilters(): void {
    this.showClientFilters = !this.showClientFilters;
  }
  toggleSatisfactionOption(value: boolean | null) {
    this.satisfactionFilter = value;
    this.applyClientFilters();
  }
  toggleressellerOption(value: boolean | null) {
    this.ressellerFilter = value;
    this.applyClientFilters();
  }
  applyClientFilters():void{
    this.allClientsData = [...this.initialClientsData];

    this.allClientsData = this.allClientsData.filter((item: any) => {

      const gender = this.globalService.getInfo('gender', item);
      const satisfaction = this.globalService.getInfo('satisfied', item);
      const resseller = this.globalService.getInfo('reseller', item);
      const segment = this.globalService.getInfo('Segment', item);
      const name = this.globalService.getInfo('customer name', item);
      const mail = this.globalService.getInfo('customer e-mail', item);




      const genderMatches = this.genderFilter!=='' && gender ? gender.toLowerCase() === this.genderFilter.toLowerCase() : true ;
      const isSatisfiedrMatches = this.satisfactionFilter === null || this.satisfactionFilter === satisfaction;
      const isressellerrMatches = this.ressellerFilter === null || this.ressellerFilter === resseller;
      const segmentMatches = this.segmentFilter!=='' && segment ? segment.toLowerCase() === this.segmentFilter.toLowerCase() : true ;
      const searchMatches = this.searchText!=='' && name || mail ? name.toLowerCase().includes(this.searchText.toLowerCase()) || mail.toLowerCase().includes(this.searchText.toLowerCase()) : true ; 


      
    return genderMatches  && isSatisfiedrMatches && isressellerrMatches && segmentMatches  && searchMatches;
  });
  }

  countClientsByField(field: string): { [key: string]: number } {
    const countMap: { [key: string]: number } = {};
    const purchaseMap: { [key: string]: number } = {};

  if (field === 'age'){
    this.allClientsData.forEach(client => {
      const age = Math.floor(client.fields[field].toFixed(0));
// console.log("age",age);
      if (age >= 0 && age <= 18) {
        countMap['0-18'] = (countMap['0-18'] || 0) + 1;
      } else if (age >= 19 && age <= 25) {
        countMap['19-25'] = (countMap['19-25'] || 0) + 1;
      } else if (age >= 26 && age <= 35) {
        countMap['26-35'] = (countMap['26-35'] || 0) + 1;
      } else if (age >= 36 && age <= 45) {
        countMap['36-45'] = (countMap['36-45'] || 0) + 1;
      } else if (age >= 46 && age <= 55) {
        countMap['46-55'] = (countMap['46-55'] || 0) + 1;
      } else if (age >= 56 && age <= 65) {
        countMap['56-65'] = (countMap['56-65'] || 0) + 1;
      } else if (age >= 66) {
        countMap['66+'] = (countMap['66+'] || 0) + 1;
      }
    });
  }
  else if (field === 'purchases') {
    // Count total purchases by age segment
    this.allClientsData.forEach(client => {
      const age = Math.floor(client.fields['age']);
      const purchases = client.fields['nbr_purchase_Monthly'] || 0;

      if (age >= 0 && age <= 18) {
        purchaseMap['0-18'] = (purchaseMap['0-18'] || 0) + purchases;
      } else if (age >= 19 && age <= 25) {
        purchaseMap['19-25'] = (purchaseMap['19-25'] || 0) + purchases;
      } else if (age >= 26 && age <= 35) {
        purchaseMap['26-35'] = (purchaseMap['26-35'] || 0) + purchases;
      } else if (age >= 36 && age <= 45) {
        purchaseMap['36-45'] = (purchaseMap['36-45'] || 0) + purchases;
      } else if (age >= 46 && age <= 55) {
        purchaseMap['46-55'] = (purchaseMap['46-55'] || 0) + purchases;
      } else if (age >= 56 && age <= 65) {
        purchaseMap['56-65'] = (purchaseMap['56-65'] || 0) + purchases;
      } else if (age >= 66) {
        purchaseMap['66+'] = (purchaseMap['66+'] || 0) + purchases;
      }
    });
  }
  else if (field === 'segmentpurchases') {
    this.allClientsData.forEach(client => {
      const segment = client.fields['Segment'];
      const purchases = client.fields['nbr_purchase_Monthly'] || 0;
      purchaseMap[segment] = (purchaseMap[segment]|| 0) + purchases;
    });
  }

  else if (field === 'genderpurchases') {
    // Count total purchases by gender
    this.allClientsData.forEach(client => {
      const gender = client.fields['gender'] ;
      const purchases = client.fields['nbr_purchase_Monthly'] || 0;
      purchaseMap[gender] = (purchaseMap[gender]|| 0) + purchases;
    });
  }

  else {
    this.allClientsData.forEach(client => {
      const fieldValue = client.fields[field];
      
    if (typeof fieldValue === 'boolean') {
      const key = fieldValue ? field : `not ${field}`;
      countMap[key] = (countMap[key] || 0) + 1;
    } else if (fieldValue) {
      countMap[fieldValue] = (countMap[fieldValue] || 0) + 1;
    }
  });
}
    // Remove keys with value 0 for non-boolean fields
    if (typeof this.allClientsData[0].fields[field] !== 'boolean') {
      Object.keys(countMap).forEach(key => {
        if (countMap[key] === 0) {
          delete countMap[key];
        }
      });
    }
  
  
    return field === 'purchases' || field === 'segmentpurchases' || field === 'genderpurchases' ? purchaseMap :  countMap;
    }


  toggleDetails(event: MouseEvent,client: any): void {
    event.stopPropagation();
     if (this.expandedClient=== client) {
       this.expandedClient = null;
     } else {
       this.expandedClient = client; 
     }
   }
   isDetailsVisible(client: any): boolean {
    return this.expandedClient === client;
  }
 

  chartPieOptions = {

    aspectRatio: 1,
    responsive: true,
    maintainAspectRatio: false,
    radius: '100%'
  
  };
  chartBarOptions: ChartOptions<'bar'> = {
    maintainAspectRatio: false,
    animation: {
      duration: 0,
    },
    responsive: true,
    scales: {
      y: {
        ticks: {
          stepSize: 1, 
        },
        beginAtZero: true,
        title: {
          display: true,
          text: 'nbr of Clients',  
        },
      },
      x: {
        ticks: {
          stepSize: 1, 
        },
        beginAtZero: true,
        title: {
          display: true,
          text: 'nbr Monthly purchases',  
        },
      },
    },
    plugins: {
      legend: {
        display: false, 

      
      },
    },
  };
 // Options for the age segmentation chart
 ageSegmentationOptions: ChartOptions<'bar'> = {
  responsive: true,
  scales: {
    x: {
      title: {
        display: true,
        text: 'Age Segments',
      },
    },
    y: {
      title: {
        display: true,
      },
      beginAtZero: true,
    },

  },

};


}
