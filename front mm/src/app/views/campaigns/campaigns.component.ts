import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit, SimpleChanges,OnDestroy } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { cibTwitter, cibLinkedin, cibYoutube, cibFacebook, cibInstagram,cilArrowThickBottom,cilWallet,cilStar,cilThumbUp,cilThumbDown,cilHandshake, cilOptions, cilCheckCircle,cilMediaRecord, cilGlobeAlt, cilListNumbered,
   cilPaperPlane, cilBellExclamation, cilBookmark, cilCalendar, cilCalendarCheck, cilUser, cilWarning, cifUs, cifAd, cifBr, cifBs,
    cifBa, cifCa, cifBb, cifCd, cifAe, cifCh, cifCi, cifBj, cifCl, cifCm, cifCo, cifCr, cifAt, cifCu, cifCv, cifBw, cifCy, cifCz, 
    cifDe, cifDj, cifDk, cifDm, cifDo, cifDz, cifEc, cifEe, cifEg, cifEr, cifEs, cifEt, cifFi, cifFj, cifFm, cifFr, cifId, cifIe, 
    cifIl, cifIn, cifIq, cifIr, cifIs, cifIt, cifJm, cifJo, cifJp, cifKe, cifKg, cifKh, cifKi, cifKm, cifKn, cifKp, cifKr, cifKw,
     cifKz, cifGa, cifGb, cifGd, cifGe, cifGh, cifGm, cifGn, cifGq, cifGr, cifGt, cifGw, cifGy, cifHk, cifHn, cifHr, cifHt, cifHu,
   cifLa, cifLb, cifLc, cifLi, cifLk, cifLr, cifLs, cifLt, cifLu, cifLv, cifLy, cifMa, cifMc, cifMd, cifMe, cifMg, cifMh, cifMk,
   cifMl, cifMm, cifMn, cifMr, cifMt, cifMu, cifMv, cifMw, cifMx, cifMy, cifMz, cifNa, cifNe, cifNg, cifNi, cifNl, cifNo, cifNp,
    cifNr, cifNu, cifNz, cifOm, cifPa, cifPe, cifPg, cifPh, cifPk, cifPl, cifPt, cifPw, cifPy, cifQa, cifRo, cifRs, cifRu, cifRw, 
    cifSa, cifSb, cifSc, cifSd, cifSe, cifSg, cifSi, cifSk, cifSl, cifSm, cifSn, cifSo, cifSr, cifSs, cifSt, cifSv, cifSy, cifSz,
    cifTd, cifTg, cifTh, cifTj, cifTl, cifTm, cifTn, cifTo, cifTr, cifTt, cifTv, cifTw, cifTz, cifUa, cifUg, cifUy, cifUz, cifVa,
    cifVc, cifVe, cifVn, cifWs, cifXk, cifYe, cifZa, cifZm, cifZw, cifAf, cifAg, cifAl, cifAm, cifAo, cifAr, cifAu, cifAz, cifBd,
    cifBe, cifBf, cifBg, cifBh, cifBi, cifBn, cifBo, cifBt, cifBy, cifBz, cifCf, cifCg, cifCn, cibCampaignMonitor, cilMonitor, 
    cilChartPie, cilFolderOpen,
    cilReload,
    cilArrowBottom,
    cilArrowTop,
    cilMinus,
    cilFrown,
    cilHappy,
    cilMeh,
    cilSearch,
    cilHeart} from '@coreui/icons';
import { IconSetService } from '@coreui/icons-angular';
import { GlobalService } from '../GlobalService';
import { BsCustomDates } from 'ngx-bootstrap/datepicker/themes/bs/bs-custom-dates-view.component';
import * as am5 from "@amcharts/amcharts5";
import * as am5xy from "@amcharts/amcharts5/xy";
import * as am5radar from "@amcharts/amcharts5/radar";
import am5themes_Animated from "@amcharts/amcharts5/themes/Animated";
import { UntypedFormControl, UntypedFormGroup } from '@angular/forms';

import { forkJoin, Observable } from 'rxjs';

@Component({
  selector: 'app-campaigns',
  templateUrl: './campaigns.component.html',
  styleUrl: './campaigns.component.scss'
})
export class CampaignsComponent implements OnInit{

  private root!: am5.Root;


searchText: any;
showFilters: boolean =false;
buy_modelFilter: string = '';
statusFilter: string = '';
uniqueLocations: any;
selectedEngagement: any;
marketing_conceptFilter: string = '';
marketing_goalFilter: string = '';
platformOptions: any;
titleFilter: string = '';
categoryOptions: any;


  allcampaignsData: any;
  initialData: any;
  alladsData: any;
  initialCampaignsData: any;
  initialAdsData: any;
  allTeamMembersData: any;
  initialTeamMembersData: any;
  allcreativeTeamssData: any;
  initialcreativeTeamsData: any;
  selectedStatusOption:  string ='';
  selectedTeamMemberOption_Execution_Period:  string ='';
  selectedTeamMemberOption_PerformanceRate : string ='';
  selectedTeamMemberOption_Top5:  string ='';
  selectedMarketingOption:  any;
  selectedMarketingKey : string ='';
  DisplayedStatusKey: string ='';
  DisplayedMarketingKey: string ='';
  StatusOptions: any;
  ConceptCount: any;
  GoalCount: any;
  selectedOption: any;
  MarketingOptions: any;
  countryFilter: any;
  platformFilter: any;
  competitorFilter: any;
  categoryFilter: any;
  date = new Date();
  startDate = new Date(this.date.getFullYear(), this.date.getMonth(), 11);
  endDate = new Date(this.date.getFullYear(), this.date.getMonth(), 17);
  BuymodelOptions: any;
  marketingConceptOptions: any;
  marketingGoalOptions: any;

  bsValue = new Date();
  bsRangeValue: Date[] = [];
  maxDate = new Date();
  minDate = new Date();
   currentYear = new Date().getFullYear();
   statusFilters: { [status: string]: boolean } = {};
   adArray:any [] = []; 
   teamArray: any[] = [];
   finalTeamsArray: any[] = [];
   CampaignAds : any ; 
  clickedcampaign : any = null;  
  clickedMember: any = null;  
  clickedteam : any = null;  
  Smile:any;
  finalAds:any; 
  chartDoughnutOptions:any;
  revenueComparisons: string[] = [];
  totalInterest: any;
  totalImpressions:any;
  totalConversions:any;
  selectedYear: string | null = null;
  AdYears: any;
  totalClicks:any;
  initialTeamsMembers:any;

  readonlyRate: boolean = true; 


  public trafficRadioGroup = new UntypedFormGroup({
    trafficRadio: new UntypedFormControl('Month')
  });

  BudgetGaugechart: any  = null;
  ConversionGaugechart: any  = null;
  ClicksGaugechart: any  = null;
  TeammemberGaugechart: any  = null;


  
  customRanges: BsCustomDates[] = [
    { label: 'Today', value: [new Date(), new Date()] },
    { label: 'Yesterday', value: [new Date(new Date().setDate(new Date().getDate() - 1)), new Date(new Date().setDate(new Date().getDate() - 1))] },
    { label: 'Last 7 Days', value: [new Date(new Date().setDate(new Date().getDate() - 6)), new Date(new Date())] },
    { label: 'This Month', value: [new Date(new Date().setDate(1)), new Date(new Date().getFullYear(), new Date().getMonth() + 1, 0)] },
    { label: 'Last Month', value: [new Date(new Date().getFullYear(), new Date().getMonth() - 1, 1), new Date(new Date().getFullYear(), new Date().getMonth(), 0)] },
    { label: 'This Year', value: [new Date(this.currentYear, 0, 1), new Date(this.currentYear, 11, 31)] },
    { label: 'Last Year', value: [new Date(this.currentYear - 1, 0, 1), new Date(this.currentYear - 1, 11, 31)] },

  ];
roleOptions: any;
roleOption: any = '';


  constructor( public globalService: GlobalService, private http: HttpClient, private cdr: ChangeDetectorRef , public iconSet: IconSetService, private dialog: MatDialog
  ) {
    iconSet.icons = {cibTwitter,cibLinkedin,cibYoutube,cibFacebook,cilFolderOpen, cibCampaignMonitor ,cibInstagram,cilWallet,cilStar,cilThumbUp,cilThumbDown,cilHeart,cilHandshake,cilSearch,cilFrown,cilHappy,cilMeh,cilArrowThickBottom,cilMediaRecord,cilReload,cilArrowBottom,cilArrowTop,cilMinus,cilMonitor,cilChartPie,cilOptions,cilCheckCircle, cilGlobeAlt, cilListNumbered, cilPaperPlane, cilBellExclamation, cilBookmark, cilCalendar , cilCalendarCheck, cilUser, cilWarning, cifUs, cifAd, cifBr, cifBs, cifBa, cifCa, cifBb,cifCd, cifAe,cifCh, cifCi, cifBj, cifCl,cifCm,cifCo,cifCr,cifAt,cifCu,cifCv, cifBw,cifCy,cifCz,cifDe,cifDj,cifDk,cifDm,cifDo,cifDz,cifEc,cifEe,cifEg,cifEr,cifEs,cifEt,cifFi,cifFj,cifFm,cifFr,cifId,cifIe,cifIl,cifIn,cifIq,cifIr,cifIs,cifIt,cifJm,cifJo,cifJp,cifKe,cifKg,cifKh,cifKi,cifKm,cifKn,cifKp,cifKr,cifKw,cifKz,cifGa,cifGb,cifGd,cifGe,cifGh,cifGm,cifGn,cifGq,cifGr,cifGt,cifGw,cifGy,cifHk,cifHn,cifHr,cifHt,cifHu,cifLa,cifLb,cifLc,cifLi,cifLk,cifLr,cifLs,cifLt, cifLu,cifLv,cifLy,cifMa,cifMc,cifMd,cifMe,cifMg,cifMh,cifMk,cifMl,cifMm,cifMn,cifMr,cifMt,cifMu,cifMv,cifMw,cifMx,cifMy,cifMz,cifNa,cifNe,cifNg,cifNi,cifNl,cifNo,cifNp,cifNr,cifNu,cifNz,cifOm,cifPa,cifPe,cifPg,cifPh,cifPk,cifPl,cifPt,cifPw,cifPy,cifQa,cifRo,cifRs,cifRu,cifRw,cifSa,cifSb,cifSc,cifSd,cifSe,cifSg,cifSi,cifSk,cifSl,cifSm,cifSn,cifSo,cifSr,cifSs,cifSt,cifSv,cifSy,cifSz,cifTd,cifTg,cifTh,cifTj,cifTl,cifTm,cifTn,cifTo,cifTr,cifTt,cifTv,cifTw,cifTz,cifUa,cifUg,cifUy,cifUz,cifVa,cifVc,cifVe,cifVn,cifWs,cifXk,cifYe,cifZa,cifZm,cifZw,cifAf,cifAg,cifAl,cifAm,cifAo,cifAr,cifAu,cifAz,cifBd,cifBe,cifBf,cifBg,cifBh,cifBi,cifBn,cifBo,cifBt,cifBy,cifBz,cifCf,cifCg,cifCn  }; 

      this.minDate.setDate(this.minDate.getDate() - 1);
      this.maxDate.setDate(this.maxDate.getDate() + 7);
      this.bsRangeValue = [];
  }


ngOnInit(){

  // this.prepareChartData();


  this.retrieveallcampaigns().subscribe((responseData: any) => {
     
      this.allcampaignsData = responseData;
      this.initialCampaignsData = responseData;
      this.StatusOptions = this.globalService.getUniqueValues('status', this.allcampaignsData);
      this.BuymodelOptions = this.globalService.getUniqueValues('buy_model', this.allcampaignsData);
      this.marketingConceptOptions =  this.globalService.getUniqueValues('marketing_concept', this.allcampaignsData);
      this.marketingGoalOptions =  this.globalService.getUniqueValues('marketing_goal', this.allcampaignsData);
      this.StatusOptions.forEach((status: any) => {
        this.statusFilters[status] = false;
      });
      console.log(" campaigns Data",this.allcampaignsData );
      console.log(" StatusOptions",this.StatusOptions );

    },
    (error: any) => {
      console.log(error);
    }
  );   

  

this.retrieveallTeamMembers().subscribe((responseData: any) => {
     
  this.allTeamMembersData = responseData;
  this.initialTeamMembersData = responseData;
  console.log("TeamMembers Data",this.allTeamMembersData );
},
(error: any) => {
  console.log(error);
}
);   

this.retrieveallcreativeTeams().subscribe((responseData: any) => {
     
  this.allcreativeTeamssData = responseData;
  this.initialcreativeTeamsData = responseData;
  console.log(" creativeTeams Data",this.allcreativeTeamssData );
},
(error: any) => {
  console.log(error);
}
);   


this.retrieveallads().subscribe((responseData: any) => {
     
  this.alladsData = responseData;
  console.log(" ads Data",this.alladsData );
},
(error: any) => {
  console.log(error);
}
);  

}

ngOnDestroy(): void {
  if (this.root) {
    this.root.dispose();
  }
}


retrieveallcampaigns (){
    const headers = new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': 'Basic',
      credentials: 'include',
    });
  
    return this.http.get<any[]>('http://localhost:8082/ExamenBlanc/Campaign/retrieve-all-campaigns', { headers });
  }
  retrieveallads (){
    const headers = new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': 'Basic',
      credentials: 'include',
    });
  
    return this.http.get<any[]>('http://localhost:8082/ExamenBlanc/Ad/retrieve-all-Ads', { headers });
  }
  retrieveallcreativeTeams (){
    const headers = new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': 'Basic',
      credentials: 'include',
    });
  
    return this.http.get<any[]>('http://localhost:8082/ExamenBlanc/Team/retrieve-all-Teams', { headers });
  }

  retrieveallTeamMembers (){
    const headers = new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': 'Basic',
      credentials: 'include',
    });
  
    return this.http.get<any[]>('http://localhost:8082/ExamenBlanc/TeamMember/retrieve-all-TeamMembers', { headers });
  }


  displayingKey(key: any, value1: any, value2: any): { DisplayedMarketingKey: string, DisplayedStatusKey: string } {
    if (key !== '' && value1 !== '') {
      if (key === 'status') {
        this.DisplayedStatusKey = key + ' : ' + value1;
      }
      if (key === 'marketing_concept' || key === 'marketing_goal') {
        this.DisplayedMarketingKey = key + ' : ' + value1;
      }
    } else {
      this.DisplayedMarketingKey = '';
      this.DisplayedStatusKey = '';
    }
  
    return { DisplayedMarketingKey: this.DisplayedMarketingKey, DisplayedStatusKey: this.DisplayedStatusKey };
  }
  

  countCampaignsByKey(data:any, key:any, value1:any , value2:any): number {
    let count: any;  
    if (key !== '' && value1 !== '' ) {
      
      count = this.globalService.CountItems(data,key, value1 , value2);    
      
    } else {
      count = data.length;
      this.DisplayedStatusKey = ''; 
      this.DisplayedMarketingKey = ''; 
    }       
    return count;
  }

  onCheckboxChange(checkedType: 'marketing_concept' | 'marketing_goal' , event: any): void {
    event.stopPropagation();
    this.MarketingOptions = this.globalService.getUniqueValues(checkedType, this.allcampaignsData);

    if (checkedType === 'marketing_concept') {
      this.GoalCount = false;

      if (this.ConceptCount === true) {this.selectedMarketingKey = 'marketing_concept' ;} else {this.selectedMarketingKey = '' ; this.selectedMarketingOption=null; this.DisplayedMarketingKey = '';}
      
    } else if (checkedType === 'marketing_goal') {
      this.ConceptCount = false;
      if (this.GoalCount === true) {this.selectedMarketingKey = 'marketing_goal' ;} else {this.selectedMarketingKey = '' ; this.selectedMarketingOption=null; this.DisplayedMarketingKey = '';}
    }
  }
 
  itemsPerPageTeam = 5; 
  itemsPerPage = 5; 
  currentPage = 1;
  currentPageTeam = 1;


  pageChanged(event: any): void {
  this.currentPage = event.page;

}

toggleFilters(): void {
  this.showFilters = !this.showFilters;
}

applyFilters(): void {


  console.log("this.bsRangeValue ", this.bsRangeValue );
  const selectedStatuses = Object.keys(this.statusFilters).filter(status => this.statusFilters[status]);

  this.allcampaignsData = [...this.initialCampaignsData];  


  this.allcampaignsData = this.allcampaignsData.filter((item: any) => {
    const buy_model = this.globalService.getInfo('buy_model', item);
    const marketing_concept = this.globalService.getInfo('marketing_concept', item);
    const marketing_goal = this.globalService.getInfo('marketing_goal', item);
    const title = this.globalService.getInfo('title', item);
    const status = this.globalService.getInfo('status', item);



    const buy_modelMatches = this.buy_modelFilter && buy_model && typeof buy_model === 'string' ?
    buy_model.toLowerCase().includes(this.buy_modelFilter.toLowerCase()) :
      true; 

      const statusMatches = selectedStatuses.length === 0 || selectedStatuses.includes(status);

     const marketing_conceptMatches = this.marketing_conceptFilter && marketing_concept && typeof marketing_concept === 'string' ?
     marketing_concept.toLowerCase().includes(this.marketing_conceptFilter.toLowerCase()) :
      true;

      const marketing_goalMatches = this.marketing_goalFilter && marketing_goal && typeof marketing_goal === 'string' ?
      marketing_goal.toLowerCase().includes(this.marketing_goalFilter.toLowerCase()) :
        true;

     const titleMatches = this.titleFilter && title && typeof title === 'string' ?
     title.toLowerCase().includes(this.titleFilter.toLowerCase()) :
     true; 

    const searchMatches = this.searchText && title && typeof title === 'string' ?
    title.toString().toLowerCase().includes(this.searchText.toLowerCase()):
    true; 

    const startDateString = this.globalService.getInfo('start_date', item);
    const endDateString = this.globalService.getInfo('end_date', item);
    
    const startDate = new Date(startDateString);
    const endDate = new Date(endDateString);
    
    const rangeStartDate = this.bsRangeValue[0];
    const rangeEndDate = this.bsRangeValue[1];
    
    const startDateWithoutTime = new Date(startDate);
    startDateWithoutTime.setHours(0, 0, 0, 0);
  
    const rangeStartDateWithoutTime = new Date(rangeStartDate);
    rangeStartDateWithoutTime.setHours(0, 0, 0, 0);
  
    const endDateWithoutTime = new Date(endDate);
    endDateWithoutTime.setHours(0, 0, 0, 0);
  
    const rangeEndDateWithoutTime = new Date(rangeEndDate);
    rangeEndDateWithoutTime.setHours(0, 0, 0, 0);
  
    const dateMatches =  this.bsRangeValue.length!==0  && startDateWithoutTime && endDateWithoutTime &&  rangeStartDateWithoutTime instanceof Date && rangeEndDateWithoutTime instanceof Date &&
                         startDateWithoutTime instanceof Date && endDateWithoutTime instanceof Date ?
                         startDateWithoutTime >= rangeStartDateWithoutTime && endDateWithoutTime <= rangeEndDateWithoutTime :
                        true;
     
  console.log("marketing_conceptMatches",marketing_conceptMatches ); 
      
      return buy_modelMatches && marketing_conceptMatches && marketing_goalMatches && titleMatches  && searchMatches 
      && statusMatches
      && dateMatches;
  });

 
  this.toggleFilters();
 
}


reset() { 

  this.bsRangeValue = [];
  this.applyFilters();
  this.toggleFilters();
}
   

async enrichAds(ads: any[]): Promise<any[]> {
      const enrichedAds: any[] = [];
    
      // Iterate over each ad
      for (const ad of ads) {
        const [campaign, team, account, product] = await Promise.all([
          this.getObjectById(ad, 'Campaign', 'Campaign_Id'),
          this.getObjectById(ad, 'Team', 'CreativeTeam_id'),
          this.getObjectById(ad, 'Account', 'Account_id'),
          this.getObjectById(ad, 'ProductSKU', 'productSKU_key')
        ]);
    
        // Create an enriched ad object
        const enrichedAd = {
          ...ad,
          campaign,
          team,
          account,
          product
        };
        // Push the enriched ad to the result array
        enrichedAds.push(enrichedAd);
      }   
      // Return the array of enriched ads
      return enrichedAds;
    }
    

  getObjectById(item: any , collection:any, foreignKey:any): Promise<any> {
    return this.globalService.getObjectById(collection, '_id', item.fields[foreignKey]).toPromise();
  }

  async CampaignDetails(Campaign: any) {
  this.adArray = [];
  this.clickedteam = null;
  const campaignId = Campaign.id;
  console.log("campaignId", campaignId);

  this.CampaignAds = this.alladsData.filter((item: any) => {
    const AdcampaignId = this.globalService.getInfo('Campaign_Id', item);

    const AdMatches = campaignId && AdcampaignId && typeof campaignId === 'string'
      ? campaignId.toLowerCase() === AdcampaignId.toLowerCase()
      : false;


    if (AdMatches) {
      this.adArray.push(item);
    }

    return AdMatches;
  });
  console.log("Filtered Ads", this.adArray);

  this.enrichAds(this.adArray).then((ads) => {
    this.finalAds = ads.sort((a:any, b:any) => b.fields.year - a.fields.year); 
    console.log('Final Ads:', this.finalAds);
    this.initialAdsData = this.finalAds;
    console.log("team", this.finalAds[0].team[0].fields.Name); 
    this.totalInterest = this.finalAds.reduce((total: any, ad: any) => {
      return total + (ad.fields.interest || 0);
    }, 0);
    this.totalImpressions = this.finalAds.reduce((total: any, ad: any) => {
      return total + (ad.fields.Impressions || 0);
    }, 0);
    this.totalConversions = this.finalAds.reduce((total: any, ad: any) => {
      return total + (ad.fields.Total_Conversion || 0);
    }, 0);
    this.totalClicks = this.finalAds.reduce((total: any, ad: any) => {
      return total + (ad.fields.Clicks || 0);
    }, 0);

    this.AdYears = this.finalAds.map((ad: any) => ad.fields.year);
    this.setAdTrafficPeriod;
    this.convertPerformanceRates();
    this.calculateRevenueComparisons();
    // this.initChart( this.finalAds.map((ad: any) => ad.fields.Spent*1000000000),this.finalAds.map((ad: any) => ad.fields.Revenu),0);
    this.GaugeChart([this.clickedcampaign.fields.daily_budget * 100 / this.clickedcampaign.fields.total_budget], "BudgetGaugechartdiv","budget");
    this.GaugeChart([this.totalConversions * 100 / this.totalInterest], "ConversionGaugechartdiv","conversion");
    this.GaugeChart([this.totalClicks * 100 / this.totalImpressions], "ClicksGaugechartdiv","clicks");


    this.prepareChartData();  
  }).catch((error) => {
    console.error('Error enriching ads:', error);
  });
  // this.initChart( [100, 120, 140, 160, 80, 60],[200, 180, 160, 140, 220, 240],0);
  
}

public gaugeChartType = 'doughnut';
public gaugeChartData: any;

getRandomColor(): string {
  const colors = [
  'red', 'orange', 'yellow', 'green', 'blue', 'purple', 'pink', 'brown', 'teal', 'cyan',
  'maroon', 'navy', 'olive', 'lime', 'indigo', 'magenta', 'peach', 'turquoise', 'lavender'
 ];
  const randomIndex = Math.floor(Math.random() * colors.length);
  return colors[randomIndex];
 }

 SliceTitle(title:any) : any{
  const commaIndex = title.indexOf(',');
  const pipeIndex = title.indexOf('|');
  let endIndex = -1;
  if (commaIndex !== -1 && pipeIndex !== -1) {
    endIndex = Math.min(commaIndex, pipeIndex);
  } else if (commaIndex !== -1) {
    endIndex = commaIndex;
  } else if (pipeIndex !== -1) {
    endIndex = pipeIndex;
  }
    return endIndex !== -1 ? title.slice(0, endIndex): title;
 }

 calculateRevenueComparisons() {

  this.revenueComparisons = [];
  this.finalAds = this.finalAds.sort((a:any, b:any) => a.fields.year - b.fields.year); 
  console.log('Final Ads calculateRevenueComparisons:', this.finalAds);
  for (let i = 0; i < this.finalAds.length; i++) {
    if (i === 0) {
      this.revenueComparisons.push('N/A'); 
    } else {
      console.log("i", i);
      const prevRevenue = this.finalAds[i - 1].fields.Revenu-(this.finalAds[i - 1].fields.Spent*1000000000);
      const currentRevenue = this.finalAds[i].fields.Revenu-(this.finalAds[i].fields.Spent*1000000000);
      const revenueDiff = currentRevenue - prevRevenue;
      console.log("prevRevenue : ", prevRevenue, "/  currentRevenue : ", currentRevenue , "  /diff : ", revenueDiff )

      if (revenueDiff > 0) {
        this.revenueComparisons.push('greater');
      } else if (revenueDiff < 0) {
        this.revenueComparisons.push('less');
      } else {
        const prevIndex = this.finalAds[i - 1].fields.index; 
        const currentIndex = this.finalAds[i].fields.index; 

        if (currentIndex < prevIndex) {
          this.revenueComparisons.push('greater');
        } else if (currentIndex > prevIndex) {
          this.revenueComparisons.push('less');
        } else {
          this.revenueComparisons.push('equal');
        }
      }
    }
  }
  console.log("revenueComparisons", this.revenueComparisons);
  this.finalAds.reverse();
  this.revenueComparisons.reverse();

 }

 getIconName(comparison: string): string {
  switch (comparison) {
    case 'greater':
      return 'cilArrowTop'; 
    case 'less':
      return 'cilArrowBottom'; 
    case 'equal':
      return 'cilMinus'; 
    default:
      return 'cilMediaRecord';
  }
}

 prepareChartData(): any {

    const labels = this.finalAds.map((ad: any) => {
    const title = ad.fields.title;
    const commaIndex = title.indexOf(',');
    const pipeIndex = title.indexOf('|');
    let endIndex = -1;
    if (commaIndex !== -1 && pipeIndex !== -1) {
      endIndex = Math.min(commaIndex, pipeIndex);
    } else if (commaIndex !== -1) {
      endIndex = commaIndex;
    } else if (pipeIndex !== -1) {
      endIndex = pipeIndex;
    }
      return endIndex !== -1 ? title.slice(0, endIndex).concat(" ", ad.fields.year) : title;
  });

  const data = this.finalAds.map((ad: any) => ad.fields.Revenu);
  console.log("data", data);



  this.gaugeChartData = {
    labels: labels,
    datasets: [
      {
        data: data,
        backgroundColor: ['blue', 'purple', 'pink', 'brown', 'teal', 'cyan', 'maroon', 'navy', 'olive', 'lime', 'indigo', 'magenta', 'peach', 'turquoise', 'lavender'],
        hoverBackgroundColor: ['blue', 'purple', 'pink', 'brown', 'teal', 'cyan', 'maroon', 'navy', 'olive', 'lime', 'indigo', 'magenta', 'peach', 'turquoise', 'lavender'],
        borderWidth: [0, 0, 0],
        
      }
    ],
   
  };
  



  this.chartDoughnutOptions = {
    aspectRatio: 1,
    responsive: true,
    maintainAspectRatio: false,
    radius: '100%',
    tooltips: { enabled: false }
  };
  
  return {labels, data}; 
}

convertPerformanceRates() {
  this.finalAds.forEach((ad: any) => {
    ad.team.forEach((member:any) => {
      console.log("team PerformanceRate",  member.fields.Performance_Rate); 
      member.fields.Performance_Rate =  Math.round(Math.round(member.fields.Performance_Rate )/ 2);
      console.log(" converted team PerformanceRate",  member.fields.Performance_Rate ); 

    });
  });
  this.finalTeamsArray.forEach((member: any) => {
    
      console.log("member PerformanceRate",  member.fields.Performance_Rate); 
      member.fields.Performance_Rate =  Math.round(Math.round(member.fields.Performance_Rate )/ 2);
      console.log(" converted member PerformanceRate",  member.fields.Performance_Rate ); 

   
  });
}

initChart(measurement1Data:any,measurement2Data:any, currentDataIndex:any ): void {

  console.log("measurement1Data : ", measurement1Data,"/ measurement2Data : ", measurement2Data);
  // Create root element
  let root = am5.Root.new("chartdiv");
  this.root = root;

  // Set themes
  root.setThemes([am5themes_Animated.new(root)]);

  // Create chart
  let chart = root.container.children.push(am5radar.RadarChart.new(root, {
    panX: false,
    panY: false,
    startAngle: 180,
    endAngle: 360,
    radius: am5.percent(90),
    layout: root.verticalLayout
  }));

  // Colors
  let colors = am5.ColorSet.new(root, { step: 2 });

  // Measurement #1
  let color1 = colors.next();

  let axisRenderer1 = am5radar.AxisRendererCircular.new(root, {
    radius: -10,
    stroke: color1,
    strokeOpacity: 1,
    strokeWidth: 6
  });

  axisRenderer1.grid.template.setAll({ forceHidden: true });
  axisRenderer1.ticks.template.setAll({
    stroke: color1,
    visible: true,
    length: 10,
    strokeOpacity: 1
  });

  axisRenderer1.labels.template.setAll({ radius: 15 });

  let xAxis1 = chart.xAxes.push(am5xy.ValueAxis.new(root, {
    maxDeviation: 0,
    min: 0,
    max:2000,
    strictMinMax: true,
    renderer: axisRenderer1
  }));

  let label1 = chart.seriesContainer.children.push(am5.Label.new(root, {
    fill: am5.color(0xffffff),
    x: -100,
    y: -60,
    width: 100,
    centerX: am5.percent(50),
    textAlign: "center",
    centerY: am5.percent(50),
    fontSize: "1em",
    text: "0M",
    background: am5.RoundedRectangle.new(root, { fill: color1 })
  }));

  let axisDataItem1 = xAxis1.makeDataItem({ value: 0 });

  let clockHand1 = am5radar.ClockHand.new(root, {
    pinRadius: 14,
    radius: am5.percent(98),
    bottomWidth: 10
  });

  clockHand1.pin.setAll({ fill: color1 });
  clockHand1.hand.setAll({ fill: color1 });

  let bullet1 = axisDataItem1.set("bullet", am5xy.AxisBullet.new(root, { sprite: clockHand1 }));
  xAxis1.createAxisRange(axisDataItem1);
  axisDataItem1.get("grid")?.set("forceHidden", true);
  axisDataItem1.get("tick")?.set("forceHidden", true);

  // Measurement #2
  let color2 = colors.next();

  let axisRenderer2 = am5radar.AxisRendererCircular.new(root, {
    stroke: color2,
    strokeOpacity: 1,
    strokeWidth: 6
  });

  axisRenderer2.grid.template.setAll({ forceHidden: true });
  axisRenderer2.ticks.template.setAll({
    stroke: color2,
    visible: true,
    length: 10,
    strokeOpacity: 1
  });

  axisRenderer2.labels.template.setAll({ radius: 15 });

  let xAxis2 = chart.xAxes.push(am5xy.ValueAxis.new(root, {
    maxDeviation: 0,
    min: 0,
    max: 4000,
    strictMinMax: true,
    renderer: axisRenderer2
  }));

  let label2 = chart.seriesContainer.children.push(am5.Label.new(root, {
    fill: am5.color(0xffffff),
    x: 100,
    y: -60,
    width: 100,
    centerX: am5.percent(50),
    textAlign: "center",
    centerY: am5.percent(50),
    fontSize: "1em",
    text: "0M",
    background: am5.RoundedRectangle.new(root, { fill: color2 })
  }));

  let axisDataItem2 = xAxis2.makeDataItem({ value: 0 });

  let clockHand2 = am5radar.ClockHand.new(root, {
    pinRadius: 10,
    radius: am5.percent(98),
    bottomWidth: 10
  });

  clockHand2.pin.setAll({ fill: color2 });
  clockHand2.hand.setAll({ fill: color2 });

  let bullet2 = axisDataItem2.set("bullet", am5xy.AxisBullet.new(root, { sprite: clockHand2 }));
  xAxis2.createAxisRange(axisDataItem2);
  axisDataItem2.get("grid")?.set("forceHidden", true);
  axisDataItem2.get("tick")?.set("forceHidden", true);

  // Legend
  let legend = chart.children.push(am5.Legend.new(root, {
    x: am5.p50,
    centerX: am5.p50
  }));
  legend.data.setAll([axisDataItem1, axisDataItem2]);

  // Function to update chart with data
  const updateChartWithData = () => {
    if (currentDataIndex < measurement1Data.length) {
      let value1 = measurement1Data[currentDataIndex]/ 1e6;
      axisDataItem1.animate({
        key: "value",
        to: value1,
        duration: 1000,
        easing: am5.ease.out(am5.ease.cubic)
      });
      label1.set("text",value1.toFixed(0).toString()+'M$');
      // this.globalService.formatNumber(value1)
      let value2 = measurement2Data[currentDataIndex]/ 1e6;
      axisDataItem2.animate({
        key: "value",
        to: value2,
        duration: 1000,
        easing: am5.ease.out(am5.ease.cubic)
      });
      label2.set("text", value2.toFixed(0).toString()+'M$');
      // this.globalService.formatNumber(value2)
      currentDataIndex++;
    }
  };

  // Set interval to update chart
  setInterval(updateChartWithData, 2000);

  // Make stuff animate on load
  chart.appear(1000, 100);
}


disposeChart(chart: any): void {
  if (chart) {
    chart.dispose();
    chart = null;
  }
}
onMemberClick(member: any) {
  this.clickedMember = member;
  if (this.clickedMember.fields.Performance_Rate *10<40){this.Smile='cilFrown';}
  if (this.clickedMember.fields.Performance_Rate *10>=40 && this.clickedMember.fields.Performance_Rate *10<=60){this.Smile='cilMeh';}
  if (this.clickedMember.fields.Performance_Rate *10 > 60){this.Smile='cilHappy';}

  setTimeout(() => {
    this.GaugeChart([this.clickedMember.fields.Performance_Rate * 10], 'TeamMemberGaugechartdiv', 'Teammember');
  }, 0);
}
GaugeChart(data: any, divname: string, chartType: string): any {
  let chartRoot: any;

  if (chartType === "budget") {
    this.disposeChart(this.BudgetGaugechart);
    chartRoot = am5.Root.new(divname);
    this.BudgetGaugechart = chartRoot;
  } else if (chartType === "conversion") {
    this.disposeChart(this.ConversionGaugechart);
    chartRoot = am5.Root.new(divname);
    this.ConversionGaugechart = chartRoot;
  }
  else if (chartType === "clicks") {
    this.disposeChart(this.ClicksGaugechart);
    chartRoot = am5.Root.new(divname);
    this.ClicksGaugechart = chartRoot;
  }
  else if (chartType === "Teammember") {
    console.log("here 1");
    this.disposeChart(this.TeammemberGaugechart);
    console.log("here 2");

    chartRoot = am5.Root.new(divname);
    console.log("here 3");

    this.TeammemberGaugechart = chartRoot;
    console.log("here 4");

  }
  

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





setAdTrafficPeriod(value: string): void {
  console.log("value", value); 

  if (this.selectedYear === value) {
    this.trafficRadioGroup.setValue({ trafficRadio: '' });
    this.finalAds = [...this.initialAdsData]; 
    this.selectedYear = null; 
  
  } else {
    this.trafficRadioGroup.setValue({ trafficRadio: value });
    this.finalAds = [...this.initialAdsData]; 
    this.finalAds = this.finalAds.filter((item: any) => {
      const year = item.fields.year;
      console.log("year", year); 

      const YearMatches = value && year ? value === year : false;

      console.log("YearMatches", YearMatches); 

      return YearMatches;
    });

    this.selectedYear = value; 
  }
  this.totalInterest = this.finalAds.reduce((total: any, ad: any) => {
    return total + (ad.fields.interest || 0);
  }, 0);
  this.totalImpressions = this.finalAds.reduce((total: any, ad: any) => {
    return total + (ad.fields.Impressions || 0);
  }, 0);
  this.totalConversions = this.finalAds.reduce((total: any, ad: any) => {
    return total + (ad.fields.Total_Conversion || 0);
  }, 0);
}




async enrichTeams(TeamMembers: any[]): Promise<any[]> {
  const enrichedTeamMembers: any[] = [];

  // Iterate over each member in TeamMembers array
  for (const memberId of TeamMembers) {
    // Retrieve the member object by its ID
    const teamMember = await this.globalService.getObjectById('TeamMember', '_id', memberId).toPromise();
    
    // Create an enriched member object
    const enrichedTeamMember = {
      teamMember,
    };
    // Push the enriched member to the result array
    enrichedTeamMembers.push(enrichedTeamMember);
  }

  // Return the array of enriched team members
  return enrichedTeamMembers;
}

async TeamDetails(team: any) {
  this.teamArray = team.fields.TeamMembers;
  this.enrichTeams(this.teamArray).then((teams) => {
    this.finalTeamsArray= teams ; 
    console.log("finalTeamsArray", this.finalTeamsArray);
    this.initialTeamsMembers = teams;
    this.roleOptions = this.globalService.getUniqueValues('teamMember.Role', this.finalTeamsArray);
    console.log("roleOptions", this.roleOptions);

  });
}

convertExecutionPeriod(period: string): number {
  const [value, unit] = period.split(' ');
  const numericValue = parseInt(value, 10);
  if (unit.includes('week')) {
    return numericValue * 7; // Convert weeks to days
  } else if (unit.includes('month')) {
    return numericValue * 30; // Convert months to days (assuming 1 month = 30 days)
  }
  return numericValue; // Default case (if unit is already in days or an unknown unit)
}

TeamMemberFilter(){

  this.finalTeamsArray = [...this.initialTeamsMembers];  

  if(this.roleOption!=='')
    {
  this.finalTeamsArray = this.finalTeamsArray.filter((item: any) => {
    let roleMatches : any; 
   item.teamMember.forEach((element:any) => {
         let member = element.fields;
          const Role = member.Role; 
           roleMatches = this.roleOption ? Role.toLowerCase() === this.roleOption.toLowerCase() : true; 
          });

          return roleMatches;

      });
}
else if (this.selectedTeamMemberOption_Top5 =='Top5') {
  this.finalTeamsArray.sort((a: any, b: any) => {
    const performanceDiff = b.teamMember[0].fields.Performance_Rate - a.teamMember[0].fields.Performance_Rate;
    if (performanceDiff === 0) {
      return this.convertExecutionPeriod(a.teamMember[0].fields.Execution_Period) - this.convertExecutionPeriod(b.teamMember[0].fields.Execution_Period);
    }
    return performanceDiff;
  });
  this.finalTeamsArray = this.finalTeamsArray.slice(0, 5); // Keep only the top 5 elements
} else if (this.selectedTeamMemberOption_Execution_Period =='Execution_Period') {
  this.finalTeamsArray.sort((a: any, b: any) => {
    return this.convertExecutionPeriod(a.teamMember[0].fields.Execution_Period) - this.convertExecutionPeriod(b.teamMember[0].fields.Execution_Period);
  });
} else if (this.selectedTeamMemberOption_PerformanceRate =='Performance_Rate') {
  this.finalTeamsArray.sort((a: any, b: any) => {
    return b.teamMember[0].fields.Performance_Rate - a.teamMember[0].fields.Performance_Rate;
  });
}
  }

  ResetTeamMemberFilter(){

    this.finalTeamsArray = [...this.initialTeamsMembers];  
    this.selectedTeamMemberOption_Top5 ='';
    this.roleOption='';
    this.selectedTeamMemberOption_Execution_Period ='';
    this.selectedTeamMemberOption_PerformanceRate ='';
  }

}


