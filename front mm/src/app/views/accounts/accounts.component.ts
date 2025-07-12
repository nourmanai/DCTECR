import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Observable, from, of } from 'rxjs';
import { catchError, map, switchMap, tap } from 'rxjs/operators';
import * as L from "leaflet";
import { iconSubset } from './../../icons/icon-subset';
 import { ChangeDetectionStrategy } from '@angular/core';
 import { MapOptions, TileLayerOptions, GeoJSONOptions } from 'leaflet';

import * as unorm from 'unorm';
import { DashboardChartsData, IChartProps } from '../dashboard/dashboard-charts-data';
import { UntypedFormGroup, UntypedFormControl } from '@angular/forms';
import { ChangeDetectorRef } from '@angular/core';
import {  OperatorFunction } from 'rxjs';

import { IconSetService } from '@coreui/icons-angular';
import { cilListNumbered, cilPaperPlane, cilBellExclamation, cilCheckCircle, cilBookmark, cilCalendar, cilCalendarCheck, cilGlobeAlt, cilUser, cilWarning, cifUs, cifAd, cifBr, cifBs, cifBa,cifCa, cifBb, cifCd,cifAe ,cifCh, cifCi, cifBj, cifCl,cifCm,cifCo,cifCr,cifAt,cifCu,cifCv, cifBw,cifCy,cifCz,cifDe,cifDj,cifDk,cifDm,cifDo,cifDz,cifEc,cifEe,cifEg,cifEr,cifEs,cifEt,cifFi,cifFj,cifFm,cifFr,cifId,cifIe,cifIl,cifIn,cifIq,cifIr,cifIs,cifIt,cifJm,cifJo,cifJp,cifKe,cifKg,cifKh,cifKi,cifKm,cifKn,cifKp,cifKr,cifKw,cifKz,cifGa,cifGb,cifGd,cifGe,cifGh,cifGm,cifGn,cifGq,cifGr,cifGt,cifGw,cifGy,cifHk,cifHn,cifHr,cifHt,cifHu,cifLa,cifLb,cifLc,cifLi,cifLk,cifLr,cifLs,cifLt, cifLu,cifLv,cifLy,cifMa,cifMc,cifMd,cifMe,cifMg,cifMh,cifMk,cifMl,cifMm,cifMn,cifMr,cifMt,cifMu,cifMv,cifMw,cifMx,cifMy,cifMz,cifNa,cifNe,cifNg,cifNi,cifNl,cifNo,cifNp,cifNr,cifNu,cifNz,cifOm,cifPa,cifPe,cifPg,cifPh,cifPk,cifPl,cifPt,cifPw,cifPy,cifQa,cifRo,cifRs,cifRu,cifRw,cifSa,cifSb,cifSc,cifSd,cifSe,cifSg,cifSi,cifSk,cifSl,cifSm,cifSn,cifSo,cifSr,cifSs,cifSt,cifSv,cifSy,cifSz,cifTd,cifTg,cifTh,cifTj,cifTl,cifTm,cifTn,cifTo,cifTr,cifTt,cifTv,cifTw,cifTz,cifUa,cifUg,cifUy,cifUz,cifVa,cifVc,cifVe,cifVn,cifWs,cifXk,cifYe,cifZa,cifZm,cifZw,cifAf,cifAg,cifAl,cifAm,cifAo,cifAr,cifAu,cifAz,cifBd,cifBe,cifBf,cifBg,cifBh,cifBi,cifBn,cifBo,cifBt,cifBy,cifBz,cifCf,cifCg,cifCn, cibInstagram, cibFacebook, cibYoutube, cibLinkedin, cibTwitter, cilOptions } from '@coreui/icons';
import { MatDialog } from '@angular/material/dialog';

import * as iso3166 from 'iso-3166-1-alpha-2';
import { AccountDetailsComponent } from '../account-details/account-details.component';
import { GlobalService } from './../GlobalService';


@Component({

  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.scss',
  changeDetection: ChangeDetectionStrategy.Default,
  

})


export class AccountsComponent implements OnInit, OnChanges {


  @Input() user: any;
  @Input() selectedRateKey: string | null = null;


  URL!: string;
  avatar$!: Observable<string>;
  iconSetService: any;
  postsCountArrayFormatted: string[] | undefined;

   cilMagnifyingGlass = iconSubset.cilMagnifyingGlass;
 
  icon = L.divIcon({
    className: 'custom-div-icon',
    html: "<div style='background-color:#c30b82;' class='marker-pin'></div><i class='material-icons'>weekend</i>",
    iconSize: [30, 42],
    iconAnchor: [15, 42]
});
  countries: { name: string, code: string }[] = [];
  mergedmarkerOptions: any;
  showVerified: boolean = false;
  showNotVerified: boolean = false;
  categoryOptions: any[] | undefined;
  showFilters: boolean =false;
  searchText = '';
  locationMap: { [location: string]: { posts_count: number; comments_count: number; rate: number ; likes_count:number; following_count:number; followers_count:number; category:string;lang:string;  [key: string]: any;}  } = {};
  selectedFilter: any;

  constructor( private globalService: GlobalService, private http: HttpClient,private chartsData: DashboardChartsData, private cdr: ChangeDetectorRef , public iconSet: IconSetService, private dialog: MatDialog
    ) {
      iconSet.icons = {cibTwitter,cibLinkedin,cibYoutube,cibFacebook ,cibInstagram,cilOptions,cilCheckCircle, cilGlobeAlt, cilListNumbered, cilPaperPlane, cilBellExclamation, cilBookmark, cilCalendar , cilCalendarCheck, cilUser, cilWarning, cifUs, cifAd, cifBr, cifBs, cifBa, cifCa, cifBb,cifCd, cifAe,cifCh, cifCi, cifBj, cifCl,cifCm,cifCo,cifCr,cifAt,cifCu,cifCv, cifBw,cifCy,cifCz,cifDe,cifDj,cifDk,cifDm,cifDo,cifDz,cifEc,cifEe,cifEg,cifEr,cifEs,cifEt,cifFi,cifFj,cifFm,cifFr,cifId,cifIe,cifIl,cifIn,cifIq,cifIr,cifIs,cifIt,cifJm,cifJo,cifJp,cifKe,cifKg,cifKh,cifKi,cifKm,cifKn,cifKp,cifKr,cifKw,cifKz,cifGa,cifGb,cifGd,cifGe,cifGh,cifGm,cifGn,cifGq,cifGr,cifGt,cifGw,cifGy,cifHk,cifHn,cifHr,cifHt,cifHu,cifLa,cifLb,cifLc,cifLi,cifLk,cifLr,cifLs,cifLt, cifLu,cifLv,cifLy,cifMa,cifMc,cifMd,cifMe,cifMg,cifMh,cifMk,cifMl,cifMm,cifMn,cifMr,cifMt,cifMu,cifMv,cifMw,cifMx,cifMy,cifMz,cifNa,cifNe,cifNg,cifNi,cifNl,cifNo,cifNp,cifNr,cifNu,cifNz,cifOm,cifPa,cifPe,cifPg,cifPh,cifPk,cifPl,cifPt,cifPw,cifPy,cifQa,cifRo,cifRs,cifRu,cifRw,cifSa,cifSb,cifSc,cifSd,cifSe,cifSg,cifSi,cifSk,cifSl,cifSm,cifSn,cifSo,cifSr,cifSs,cifSt,cifSv,cifSy,cifSz,cifTd,cifTg,cifTh,cifTj,cifTl,cifTm,cifTn,cifTo,cifTr,cifTt,cifTv,cifTw,cifTz,cifUa,cifUg,cifUy,cifUz,cifVa,cifVc,cifVe,cifVn,cifWs,cifXk,cifYe,cifZa,cifZm,cifZw,cifAf,cifAg,cifAl,cifAm,cifAo,cifAr,cifAu,cifAz,cifBd,cifBe,cifBf,cifBg,cifBh,cifBi,cifBn,cifBo,cifBt,cifBy,cifBz,cifCf,cifCg,cifCn  }; 
      


  };


  public colors : String[]= ['primary', 'success', 'info', 'danger',  'warning', 'secondary', 'dark', 'light' ];

 
 
  
  public mainChart: IChartProps = {};
  public chart: Array<IChartProps> = [];
  public trafficRadioGroup = new UntypedFormGroup({
    trafficRadio: new UntypedFormControl('Month')
  });



  allaccountsData : any;
  dataArray:any;
  uniqueLocations!: string[] | undefined;
  rateCommentArray!: number[] | undefined;
  rateLikeArray!: number[] | undefined;

  commentsCountArray!:number[] | undefined;
  postsCountArray!: number[] | undefined;
  likesCountArray!: number[] | undefined;
  followersCountArray!: number[] | undefined;
  followingCountArray!: number[] | undefined;
  chartOptions: any = {};
  chartBarData: any = {};
  chartLineData: any = {};
  chartLineData2: any = {};
  Scatterdata : any = {};
  jsonUrl = './assets/files/countries.json';
  jsonData: any;
  map: L.Map | undefined;
  defaultMarkerPosition: { lat: number; lng: number } = { lat: 51, lng: -9 };

   latLngObjects: L.LatLng[] | undefined ; 
   showDetails: boolean = false;
   expandedUser: any = null;

   countryFilter: string = ''; 
   platformFilter: string = ''; 
   selectedEngagement: string = '';
   selectedMapFilter: string = '';

   initialData: any;
   competitorFilter: string = '';
   categoryFilter :string = '';
    MyAccount : any;
   platformOptions: { name: string, value: string, icon: string }[] = [
    { name: 'Facebook', value: 'facebook', icon: 'cibFacebook'},
    { name: 'YouTube', value: 'youtube', icon: 'cibYoutube' },
    { name: 'Twitter', value: 'twitter', icon: 'cibTwitter' },
    { name: 'Instagram', value: 'instagram', icon: 'cibInstagram' },
    { name: 'LinkedIn', value: 'linkedin', icon: 'cibLinkedin' },
    { name: 'Other', value: 'other', icon: '' },

  ];


  getAccountbyKeyValue(data: any, key: any, value: any) : any{
    console.log("get function", this.globalService.getEntityByKey(data,key,value));
    return this.globalService.getEntityByKey(data,key,value);
  }
  getCategoryOptions() : void{
    const categories = this.allaccountsData.map((item: any) => this.getInfo('category', item)); 
      const uniquecategorySet = new Set(categories);
      this.categoryOptions = Array.from(uniquecategorySet);
      console.log("getCategoryOptions", this.categoryOptions ); 
  }
  

  getPlatformIcon(platformValue: string): string  {
    const foundOption = this.platformOptions.find(option => option.name === platformValue);
    return foundOption?.icon || '';
  }
  
   EngagementFilter():string {
    return this.selectedEngagement === 'likes' ? 'likes_count': 'comments_count';
   }

   onCheckboxChange(checkedType: 'verified' | 'notVerified'): void {
    if (checkedType === 'verified') {
      this.showNotVerified = false; 
    } else if (checkedType === 'notVerified') {
      this.showVerified = false; 
    }
    this.applyFilters();
  }
  applyFilters(): void {
    this.allaccountsData = [...this.initialData];  
    this.allaccountsData = this.allaccountsData.filter((item: any) => {
      const country = this.getInfo('location', item);
      const platform = this.getInfo('platform', item);
      const competitor = this.getInfo('competitor', item);
      const isVerified = this.getInfo('verified', item);
      const category = this.getInfo('category', item);
      const username =  this.getInfo('username', item)|| this.getInfo('name', item);

      const countryMatches = this.countryFilter && country && typeof country === 'string' ?
        country.toLowerCase().includes(this.countryFilter.toLowerCase()) :
        true; 
  
      const platformMatches = this.platformFilter && platform && typeof platform === 'string' ?
        platform.toLowerCase().includes(this.platformFilter.toLowerCase()) :
        true;

      let competitorMatches = true;
      if (this.competitorFilter === 'competitor') {
          competitorMatches = competitor === true;
         } else if (this.competitorFilter === 'regular') {
              competitorMatches = competitor === false;
            };

            
      let verifiedMatches = true;

      if (this.showVerified && !this.showNotVerified) {
        
        verifiedMatches = isVerified === true; 
      
      } else if (!this.showVerified && this.showNotVerified) {
          verifiedMatches = isVerified === false; 
      
      } else {
        this.allaccountsData = [...this.initialData];  
      }

      const categoryMatches = this.categoryFilter && category && typeof category === 'string' ?
      category.toLowerCase().includes(this.categoryFilter.toLowerCase()) :
      true; 

      const searchMatches = this.searchText && username && typeof username === 'string' ?
      username.toString().toLowerCase().includes(this.searchText.toLowerCase()):
      true; 



            return countryMatches && platformMatches && competitorMatches && verifiedMatches && categoryMatches && searchMatches;
    });

    this.toggleFilters();
   
    //this.resetFilters();  // removed  it so i can use all filters together 
  }
  toggleFilters(): void {
    this.showFilters = !this.showFilters;
  }
  resetFilters(): void {
    this.countryFilter = ''; 
    this.platformFilter = ''; 
  }
   formatNumber(value: number): string {
    if (Math.abs(value) >= 1000000) {
      return (value / 1000000).toFixed(1) + 'M';
    } else if (Math.abs(value) >= 1000) {
      return (value / 1000).toFixed(1) + 'K';
    } else {
      return value.toFixed(0); 
    }
  } 


  showAccountDetails(user: any): void {
    console.log("showAccountDetails  user", user);
    this.dialog.open( AccountDetailsComponent, { 
      width: 'auto', 
      height: 'auto',
      data: {user} 
    });

  }

   toggleDetails(event: MouseEvent,user: any): void {
    event.stopPropagation();
     if (this.expandedUser === user) {
       this.expandedUser = null;
     } else {
       this.expandedUser = user; 
     }
   }
 
   isDetailsVisible(user: any): boolean {
     return this.expandedUser === user;
   }
  
   removeSpecialCharacters(str: string): string {
    const accentedChars = /[À-ÿ]/g;
    const nonAccentedChars = str.replace(accentedChars, function(char) {
      const nonAccentedChar = char.normalize('NFD').replace(/[\u0300-\u036f]/g, '');
      return nonAccentedChar;
    });
    return nonAccentedChars.replace(/[^a-zA-Z0-9\s]/g, '');
  }

   countryNameMappings: { [key: string]: string } = {
    'brasil': 'Brazil',
    'españa': 'Spain'
  };

  standardizeCountryName(country: string): string {
    const standardCountryName = (this.countryNameMappings[country.toLowerCase()]) || country;
    return standardCountryName;
  }
  //cilGlobeAlt
   loadCountries(location: any): string {
    const standardCountryName = this.standardizeCountryName(location);
    const countryCode = iso3166.getCode(this.removeSpecialCharacters(standardCountryName));
    if (countryCode && countryCode.length >= 2) {
      return countryCode.toUpperCase();
    } else {
      return ''; 
    }
  }
  getFlagIcon(countryCode: string): string {
    return `cif-${countryCode.toLowerCase()}`;
  }

   initCharts(): void {
    this.mainChart = this.chartsData.mainChart;
  }

  setTrafficPeriod(value: string): void {
    this.trafficRadioGroup.setValue({ trafficRadio: value });
    this.chartsData.initMainChart(value);
    this.initCharts();
  }


  handleImageError(event: Event): void {
    const imgElement = event.target as HTMLImageElement;
  
    // Check if the image URL resulted in a 404 error
    const is404Error = imgElement.complete && imgElement.naturalHeight === 0;
  console.log ("404:" , is404Error);
    if (is404Error) {
      console.log ("404: innn" );

      // Set a fallback image or a blank icon URL
      const blankIconUrl = './assets/img/avatars/10.jpg';
      // You can also set it to an empty string for no icon
      // const blankIconUrl = '';
  
      // Set the src attribute to the fallback image URL
      imgElement.src = blankIconUrl;
    }
  }
  
  
  public getRandom(): any {
    const randomIndex = Math.floor(Math.random() * this.colors.length);
    const randomColor = this.colors[randomIndex];
    return randomColor;
  }

  avatarUrl: string = './assets/img/avatars/10.jpg'; 
  
  ngOnInit(): void {
    console.log("Initializing with RateKey:", this.RateKey);
    setTimeout(() => {
      this.updateChart();
    }, 100);
        this.initCharts();
    this.retrieveallaccounts()
    .pipe(
      switchMap((responseData: any) => {
        this.allaccountsData = responseData;
        this.initialData = responseData;
       
        return this.chartData(this.allaccountsData);
      }),
      tap((chartData: any) => {
        this.postsCountArray = chartData.postsCountArray;
        this.rateCommentArray = chartData.rateCommentArray;
        this.rateLikeArray = chartData.rateLikeArray;
        this.uniqueLocations = chartData.uniqueLocations;
        this.commentsCountArray = chartData.commentsCountArray;
        this.likesCountArray = chartData.likesArray;
        this.followersCountArray = chartData.followersArray;
        this.followingCountArray=chartData.followingArray;
        this.locationMap = chartData.locationMap;
      })
    )
    .subscribe(
      () => {
       
        // this.buildRateBarChartOptions( this.RateKey);
        this.buildLineChartOptions() ;
        this.buildLineChartOptions2();
        this.loadAvatarUrl() ;
        this.getCategoryOptions();
        this.MyAccount= this.getAccountbyKeyValue(this.allaccountsData,'username', 'CampaignTechUK');
        console.log("MyAccount",this.MyAccount );
       

      },
      (error: any) => {
        console.log(error);
      }
    ); 
   

  }
  retrieveallaccounts() {
    const headers = new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': 'Basic',
      credentials: 'include',
    });
  
    return this.http.get<any[]>('http://localhost:8082/ExamenBlanc/Account/retrieve-all-accounts', { headers });
  }
    
  //object Values To Array
    objectValuesToArray(obj: { [key: string]: any }): any[] {
      return Object.values(obj);
    }
  
    
  chartData(Data: any) {
    this.dataArray = this.objectValuesToArray(Data);
    if (!Array.isArray(Data) || Data.length === 0) {
      console.error('Invalid or empty data array.');
      return of({
        uniqueLocations: [],
        rateArray: [],
        commentsCountArray: [],
        postsCountArray: [],
      });
    }
  
    const locationCounts: { [location: string]: { posts_count: number; comments_count: number; rateComments: number; rateLikes: number ; likes_count:number; following_count:number; followers_count:number; category:string;lang:string; } } = {};
      this.dataArray.forEach((element: { fields: { location: any; posts_count: any; comments_count: any ;likes_count:any; following_count:any; followers_count:any; category:any;lang:any} }) => {
        const location = element?.fields?.location ?? 'Unknown';
        const postsCount = element?.fields?.posts_count ?? 0;
        const commentsCount = element?.fields?.comments_count ?? 0;
        const likesCount = element?.fields?.likes_count ?? 0;
        const followingCount = element?.fields?.following_count ?? 0;
        const followersCount = element?.fields?.followers_count ?? 0;
    
        if (!locationCounts[location]) {
          locationCounts[location] = { posts_count: 0, comments_count: 0 ,  rateComments: 0, rateLikes: 0, likes_count:0,following_count:0,followers_count:0,category:'',lang:''};
        }
        locationCounts[location].posts_count += postsCount;
        locationCounts[location].comments_count += commentsCount;
        locationCounts[location].likes_count += likesCount;
        locationCounts[location].following_count += followingCount;
        locationCounts[location].followers_count += followersCount;
      });
    
      // Calculate rate for each location
      Object.keys(locationCounts).forEach(location => {
        const totalPosts = locationCounts[location].posts_count;
        const totalComments = locationCounts[location].comments_count;
        const totalLikes = locationCounts[location].likes_count;

        locationCounts[location].rateComments = totalPosts !== 0 ? totalComments  / totalPosts : 0;
        locationCounts[location].rateLikes = totalPosts !== 0 ? totalLikes  / totalPosts : 0;

      });
    
      const uniqueLocations = Object.keys(locationCounts);
      const postsCountArray = uniqueLocations?.map(location => locationCounts[location].posts_count/1000);
      const commentsCountArray = uniqueLocations?.map(location => locationCounts[location].comments_count/1000);
      const rateCommentArray = uniqueLocations?.map(location => locationCounts[location].rateComments *100);
      const rateLikeArray = uniqueLocations?.map(location => locationCounts[location].rateLikes *100);
      const likesArray = uniqueLocations?.map(location => locationCounts[location].likes_count/1000);
      const followersArray = uniqueLocations?.map(location => locationCounts[location].followers_count/1000);
      const followingArray = uniqueLocations?.map(location => locationCounts[location].following_count/1000);
      const totalLikes =  uniqueLocations?.map(location => locationCounts[location].likes_count/1000);
      const locationMap = locationCounts;
  
    return of({
      uniqueLocations: uniqueLocations,
      postsCountArray: postsCountArray, 
      rateCommentArray: rateCommentArray, 
      rateLikeArray: rateLikeArray, 
      commentsCountArray: commentsCountArray, 
      likesArray :likesArray,
      followersArray :followersArray,
      followingArray:followingArray,
      totalLikes:totalLikes,
      locationMap:locationMap,
    });
  }


  itemsPerPage = 5; 
  currentPage = 1;

  pageChanged(event: any): void {
  this.currentPage = event.page;

}


handleAvatarError(event: any): void {
  console.error('Avatar loading error occurred:', event);

  this.user.profile_image_url = './assets/img/avatars/10.jpg';
}
  getInfo(property: string, data: any): any | undefined {
    return data ? data.fields[property] : undefined;
  }

   async loadAvatarUrl(): Promise<void> {

    const imageUrl = this.getInfo('profile_image_url', this.user);


    if (imageUrl) {

      try {
        const response = await this.http.head(imageUrl, { observe: 'response' }).toPromise();
        if (response?.status === 200) {
          this.avatarUrl = imageUrl; // Update avatar URL if image is valid

        }
      } catch (error) {
        console.error('Failed to load image:', error);
      }
    }

  }
  geturl(user:any): string {
     this.loadAvatarUrl();
     return this.avatarUrl;
  }
  
  ngOnChanges(changes: SimpleChanges): void {
    this.cdr.markForCheck();
    if (changes['user'] && !changes['user'].firstChange) {
      this.user.profile_image_url = ['user'];
      console.log("changes",this.user.profile_image_url )
    }
  }

    truthy<T>(): OperatorFunction<T, T> {
    return (source: Observable<T>) =>
      new Observable<T>((subscriber) => {
        return source.subscribe({
          next: (value) => {
            if (value) {
              subscriber.next(value);
            }
          },
          error: (error) => {
            subscriber.error(error);
          },
          complete: () => {
            subscriber.complete();
          },
        });
      });
  }
  

  setAvatarUrl(user: any): Observable<string> {
    const url = this.getInfo('profile_image_url', user) ?? './assets/img/avatars/10.jpg';
    return this.checkImageUrl(url).pipe(
      map((exists) => exists ? url : './assets/img/avatars/10.jpg'),
      this.truthy()
    );
  }

  checkImageUrl(url: string): Observable<boolean> {
    return from(fetch(url, { method: 'HEAD' })).pipe(
      map(response => response.ok),
      catchError(() => of(false))
    );
  }


  



// bar chart :: Rate 

RateKey: string = '';
// Call this function whenever you want to update the chart data
updateChart(): void {
  console.log("Updating chart with RateKey: before", this.RateKey);
  this.chartBarData = this.buildRateBarChartOptions(this.RateKey);

  console.log("Updating chart with RateKey after:", this.chartBarData);
}


buildRateBarChartOptions(key: any): any {  
  if (key === 'comments' || key === 'likes') {
    const array = key === 'comments' ? this.rateCommentArray : this.rateLikeArray;
    return {
      labels: this.uniqueLocations,
      datasets: [
        {
          label: 'Rate',
          yAxisLabel: 'Rate',
          backgroundColor: 'rgba(151, 187, 205, 0.2)',
          borderColor: 'rgba(151, 187, 205, 1)',
          borderWidth: 2,
          data: array, 
          tooltip: {
            enabled: true,
            callbacks: {
              label: (tooltipItem: any) => {
                return tooltipItem.formattedValue + ' (%)';
              },
            },
          },
        },
      ],
      options: {
        scales: {
          y: {
            ticks: {
              callback: (value: any) => {
                return value + ' %';
              },
            },
          },
        },
      },
    };
  } else {
    return {
      labels: this.uniqueLocations,
      datasets: [
        {
          label: 'Likes Rate',
          yAxisID: 'y1',
          backgroundColor: 'rgba(151, 187, 205, 0.2)',
          borderColor: 'rgba(151, 187, 205, 1)',
          borderWidth: 2,
          data: this.rateLikeArray, 
          tooltip: {
            enabled: true,
            callbacks: {
              label: (tooltipItem: any) => {
                return tooltipItem.formattedValue + ' (%)';
              },
            },
          },
        },
        {
          label: 'Comments Rate',
          yAxisID: 'y2',
          backgroundColor: 'rgba(255, 99, 132, 0.2)',
          borderColor: 'rgba(255, 99, 132, 1)',
          borderWidth: 2,
          data: this.rateCommentArray, 
          tooltip: {
            enabled: true,
            callbacks: {
              label: (tooltipItem: any) => {
                return tooltipItem.formattedValue + ' (%)';
              },
            },
          },
        },
      ],
      options: {
        scales: {
          y1: {
            position: 'left',
            ticks: {
              callback: (value: any) => {
                return value + ' %';
              },
            },
          },
          y2: {
            position: 'right',
            ticks: {
              callback: (value: any) => {
                return value + ' %';
              },
            },
          },
        },
      },
    };
  }
}



  
   
    //  Comments and posts count by Location (country) 
  
 
    
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
            callback: function(value: any, _index: any, _values: any) {
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


    // Likes , Followers by location (country)  

    buildLineChartOptions2() : void{
      // backgroundColor: 'rgba(151, 187, 205, 0.2)',
      // borderColor: 'rgba(151, 187, 205, 1)',
  
      this.chartLineData2 = {
        labels: this.uniqueLocations,    
        datasets: [
          {
            stacked: true,
            label: 'Likes',
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            borderColor: 'rgba(255, 99, 132, 1)',
            // backgroundColor: 'rgba(220, 220, 220, 0.2)',
            // borderColor: 'rgba(220, 220, 220, 1)',
            // pointBackgroundColor: 'rgba(220, 220, 220, 1)',
            // pointBorderColor: '#fff',
            borderWidth: 2,

            data: this.likesCountArray,
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
            stacked: true,
            label: 'Followers',
            backgroundColor: 'rgba(151, 187, 205, 0.2)',
            borderColor: 'rgba(151, 187, 205, 1)',
            // pointBackgroundColor: 'rgba(151, 187, 205, 1)',
            // backgroundColor: 'rgba(255, 99, 132, 0.2)',
            // borderColor: 'rgba(255, 99, 132, 1)',
            // pointBackgroundColor: 'rgba(255, 99, 132, 1)',
            // pointBorderColor: '#fff',
            borderWidth: 2,

            data:this.followersCountArray,
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
          //   stacked: true,
          //   label: 'Following',
          //   backgroundColor: 'rgba(255, 99, 132, 0.2)',
          //   borderColor: 'rgba(255, 99, 132, 1)',
          //   pointBackgroundColor: 'rgba(255, 99, 132, 1)',
          //   pointBorderColor: '#fff',
          //   borderWidth: 2,
          //   data: this.followingCountArray,
          //   tooltip: {
          //     enabled: true,
          //     callbacks: {
          //       label: (tooltipItem: any) => {
          //        return tooltipItem.formattedValue +' (k)';
          //        },
                
          //     },
  
             
          //   },
          // },
        ],
      
      };
    }

    chartBarOptions2 = {
      maintainAspectRatio: false,
     
      responsive: true,
      scales: {
        x: {
          stacked: true,
     
        },
        y: {
          stacked: true,
          ticks: {
            callback: function(value: any, _index: any, _values: any) {
              return value/1000 + ' M';
            },
          }
        }
      }
      
    };
   

  
    
//map

    options : MapOptions= {
      center: [52.3, 8.0],
      zoom: 3,
      scrollWheelZoom: false,
    
    };
    

    getRandomColor(): string {
       const colors = [
       'red', 'orange', 'yellow', 'green', 'blue', 'purple', 'pink', 'brown', 'teal', 'cyan',
       'maroon', 'navy', 'olive', 'lime', 'indigo', 'magenta', 'peach', 'turquoise', 'lavender'
      ];
       const randomIndex = Math.floor(Math.random() * colors.length);
       return colors[randomIndex];
      }

     //onMapReady without filter
     onMapReadyNoFilter(map: L.Map): void {
      this.map = map;
    
      const tileLayerOptions: TileLayerOptions = {
        maxZoom: 18,
        tileSize: 512,
        zoomOffset: -1,
        attribution: 'OpenStreetMap',
      };
    
      const tileLayer = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', tileLayerOptions);
    
      tileLayer.addTo(map);
    
      this.http.get<any>(this.jsonUrl).subscribe((data: any) => {
        if (data && data.features) {
          const geoJSONOptions: GeoJSONOptions = {
            style: (feature: any) => ({
              color: 'black',
              fillColor: feature ? 'white' : 'white',
              opacity: 0.05,
              weight: 1.9,
              dashArray: '2',
              fillOpacity: 0,
            }),
            onEachFeature: (feature: any, layer: any) => {
            
              layer.on({
                mouseover: (e: { target: any }) => {
                  const layer = e.target;
                  if (feature.properties && feature.properties.FORMAL_EN) {
                    const formalEnNormalized = unorm.nfkd(feature.properties.SOVEREIGNT.toLowerCase()); 
                  
                    const foundLocation = this.uniqueLocations?.find(location =>
                      unorm.nfkd(this.removeSpecialCharacters(this.standardizeCountryName(location)).toLowerCase()).includes(formalEnNormalized)
                    );
                  
                    if (foundLocation) {
                               const popupContent = `
                               Country: ${foundLocation}<br>
                               Posts Count: ${this.formatNumber(this.locationMap[foundLocation].posts_count)}<br>
                               Followers Count: ${this.formatNumber(this.locationMap[foundLocation].followers_count)}<br>
                               Likes Count: ${ this.formatNumber(this.locationMap[foundLocation].likes_count)}<br>
                               Comments Count: ${ this.formatNumber(this.locationMap[foundLocation].comments_count)} `;
                            layer.bindPopup(popupContent);
                                         
                      } else {
                      layer.bindPopup("No information found for this location: " + formalEnNormalized );
                    }
                  } else {
                    layer.bindPopup("Country not available for this feature.");
                  }
                  layer.setStyle({
                    fillColor: this.getRandomColor(),
                    fillOpacity: 0.2,
                  });
                },
                mouseout: (e: { target: any }) => {
                  const layer = e.target;
                  layer.setStyle({
                    fillColor: 'white',
                    fillOpacity: 0,
                  });
                },
              });
            },
          };
    
          L.geoJSON(data.features, geoJSONOptions).addTo(map);
        }
      });
    }
    
 
    // onMapReady with filter

     createChoroplethMap(map: L.Map, geoData: any, Filterkey: any): void {

      console.log("Filterkey", Filterkey); 
        const tileLayerOptions: L.TileLayerOptions = {
            maxZoom: 18,
            tileSize: 512,
            zoomOffset: -1,
            attribution: 'OpenStreetMap',
        };
        const tileLayer = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', tileLayerOptions);
        tileLayer.addTo(map);
    
        // console.log("locationMap",  this.locationMap);
        const style = (feature: any): L.PathOptions => {

          const formalEnNormalized = unorm.nfkd(feature.properties.SOVEREIGNT.toLowerCase());                         
          const foundLocation = this.uniqueLocations?.find(location =>
              unorm.nfkd(this.removeSpecialCharacters(this.standardizeCountryName(location)).toLowerCase()).includes(formalEnNormalized)
              );

              const locationData = foundLocation ? this.locationMap[foundLocation] : undefined;

          if (!locationData) {           
            // console.log(formalEnNormalized , "not found ");  
              return {
                  fillColor: 'white', 
                  weight: 1,
                  opacity: 1,
                  color: 'white',
                  fillOpacity: 0
              };
          }
 else {
          const Filter = locationData[Filterkey];
          const fillColor = getColor(Filter);           
          // console.log(formalEnNormalized , "  found and locationData : " ,locationData );  
          console.log("Filter", Filter); 

          return {
              fillColor: fillColor,
              weight: 1,
              opacity: 1,
              color: fillColor,
              fillOpacity: 0.7
          };
        }
      };

    
    const getColor = (Filter: any): string => {
      const key = Filter;
      //  console.log(" filter key", key);
      return key > 10000000 ? '#800026' :
             key > 1000000 ? '#BD0026' :
             key > 500000 ? '#E31A1C' :
             key > 100000 ? '#FC4E2A' :
             key > 10000 ? '#FD8D3C' :
             key > 5000 ? '#FEB24C' :
             key > 1000 ? '#FED976' :
                          '#FFEDA0';
}
  
        const onEachFeature = (feature: any, layer: L.Layer): void => {
          let foundLocation: string | undefined; 
          let originalFillColor: string | undefined;
          let originalFillOpacity: string | undefined;


              layer.on({
                        mouseover: (e: { target: any }) => {
                          const layer = e.target;
                          if (feature.properties && feature.properties.FORMAL_EN) {
                            const formalEnNormalized = unorm.nfkd(feature.properties.SOVEREIGNT.toLowerCase()); 
                          
                             foundLocation = this.uniqueLocations?.find(location =>
                              unorm.nfkd(this.removeSpecialCharacters(this.standardizeCountryName(location)).toLowerCase()).includes(formalEnNormalized)
                            );
                          
                            if (foundLocation) {
                                       const popupContent = `
                                       Country: ${foundLocation}<br>
                                       Posts Count: ${this.formatNumber(this.locationMap[foundLocation].posts_count)}<br>
                                       Followers Count: ${this.formatNumber(this.locationMap[foundLocation].followers_count)}<br>
                                       Likes Count: ${ this.formatNumber(this.locationMap[foundLocation].likes_count)}<br>
                                       Comments Count: ${ this.formatNumber(this.locationMap[foundLocation].comments_count)} `;                                    
                                    layer.bindPopup(popupContent);
                                                 
                              } else {
                              layer.bindPopup("No information found for this location: " + formalEnNormalized );
                            }
                          } else {
                            layer.bindPopup("Country not available for this feature.");
                          }
                          originalFillColor = layer.options.fillColor;
                          originalFillOpacity = layer.options.fillOpacity;
                          layer.setStyle({
                            fillColor: this.getRandomColor(),
                            fillOpacity: 0.2,
                          });
                        },
                        mouseout: (e: { target: any }) => {
                          const layer = e.target;  
                           layer.setStyle({
                          fillColor: originalFillColor,
                          fillOpacity: originalFillOpacity,
                        });               
                        },
                      });
                 
        }
    
        L.geoJSON(geoData, {
            style: style,
            onEachFeature: onEachFeature
        }).addTo(map);


        const generateLegendHTML = (): string => {
          const legendItems = [
            { threshold: 10000000, color: '#800026', label: '> 10M' },
            { threshold: 1000000, color: '#BD0026', label: '> 1M' },
            { threshold: 500000, color: '#E31A1C', label: '> 500K' },
            { threshold: 100000, color: '#FC4E2A', label: '> 100K' },
            { threshold: 10000, color: '#FD8D3C', label: '> 10K' },
            { threshold: 5000, color: '#FEB24C', label: '> 5K' },
            { threshold: 1000, color: '#FED976', label: '> 1K' },
            { threshold: 0, color: '#FFEDA0', label: '0 - 1K' }
          ];
      
          let html = `<div   style="display: flex; justify-content: center;align-items: center;">`;
          html += `<div  style=" margin: 1px 5px; border-radius: 5px;color: black;font-size: 0.5px; font-weight: bold;">
          ${Filterkey}</div>`;
        
          legendItems.forEach(item => {
              html += `<div  style="background-color: ${item.color};  padding: 0px 2px;
              margin: 1px 3px;
              border-radius: 5px;
              color: white;
              font-size: 0.5px;
              font-weight: bold;">${item.label}</div>`;
          });
          html += '</div>';
          return html;
        }
      
       // Define a custom legend control
      const LegendControl = L.Control.extend({
           options: {
          position: 'bottomright'
            },

         onAdd: function (map: L.Map) {
         const div = L.DomUtil.create('div', 'legend');
         div.innerHTML = generateLegendHTML();
          return div;
  }
});

// Create the legend control and add it to the map
const legendControl = new LegendControl();
legendControl.addTo(map);


    }
    
    onMapReadyWithFilter(map: L.Map, key : any ) : void {
      this.http.get<any>(this.jsonUrl).subscribe((data: any) => {
        if (data && data.features) {
            this.createChoroplethMap(map, data.features, key);
            
        }
    });
  }


    applyMapFilters(selectedFilter: string): void {
      console.log("selectedFilter",selectedFilter);
      this.cdr.detectChanges();
    }


    

}
