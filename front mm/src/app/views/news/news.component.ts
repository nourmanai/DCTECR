import {  ChangeDetectorRef, Component, OnInit, Pipe, PipeTransform, SimpleChanges,OnChanges } from '@angular/core';
import { cibCampaignMonitor, cibFacebook, cibInstagram, cibLinkedin, cibTwitter, cibYoutube, cifAd, cifAe, cifAf, cifAg, cifAl, cifAm, cifAo, cifAr, cifAt, cifAu, cifAz, cifBa, cifBb, cifBd, cifBe, cifBf, cifBg, cifBh, cifBi, cifBj, cifBn, cifBo, cifBr, cifBs, cifBt, cifBw, cifBy, cifBz, cifCa, cifCd, cifCf, cifCg, cifCh, cifCi, cifCl, cifCm, cifCn, cifCo, cifCr, cifCu, cifCv, cifCy, cifCz, cifDe, cifDj, cifDk, cifDm, cifDo, cifDz, cifEc, cifEe, cifEg, cifEr, cifEs, cifEt, cifFi, cifFj, cifFm, cifFr, cifGa, cifGb, cifGd, cifGe, cifGh, cifGm, cifGn, cifGq, cifGr, cifGt, cifGw, cifGy, cifHk, cifHn, cifHr, cifHt, cifHu, cifId, cifIe, cifIl, cifIn, cifIq, cifIr, cifIs, cifIt, cifJm, cifJo, cifJp, cifKe, cifKg, cifKh, cifKi, cifKm, cifKn, cifKp, cifKr, cifKw, cifKz, cifLa, cifLb, cifLc, cifLi, cifLk, cifLr, cifLs, cifLt, cifLu, cifLv, cifLy, cifMa, cifMc, cifMd, cifMe, cifMg, cifMh, cifMk, cifMl, cifMm, cifMn, cifMr, cifMt, cifMu, cifMv, cifMw, cifMx, cifMy, cifMz, cifNa, cifNe, cifNg, cifNi, cifNl, cifNo, cifNp, cifNr, cifNu, cifNz, cifOm, cifPa, cifPe, cifPg, cifPh, cifPk, cifPl, cifPt, cifPw, cifPy, cifQa, cifRo, cifRs, cifRu, cifRw, cifSa, cifSb, cifSc, cifSd, cifSe, cifSg, cifSi, cifSk, cifSl, cifSm, cifSn, cifSo, cifSr, cifSs, cifSt, cifSv, cifSy, cifSz, cifTd, cifTg, cifTh, cifTj, cifTl, cifTm, cifTn, cifTo, cifTr, cifTt, cifTv, cifTw, cifTz, cifUa, cifUg, cifUs, cifUy, cifUz, cifVa, cifVc, cifVe, cifVn, cifWs, cifXk, cifYe, cifZa, cifZm, cifZw, cilArrowBottom, cilArrowThickBottom, cilArrowTop, cilBellExclamation, cilBookmark, cilCalendar, cilCalendarCheck, cilCart, cilCash, cilChartPie, cilCheckCircle, cilFilterX, cilFolderOpen, cilFrown, cilGlobeAlt, cilHandshake, cilHappy, cilHeart, cilList, cilListFilter, cilListNumbered, cilMediaRecord, cilMeh, cilMinus, cilMonitor, cilOptions, cilPaperPlane, cilPuzzle, cilReload, cilSearch, cilStar, cilTag, cilThumbDown, cilThumbUp, cilUser, cilWallet, cilWarning } from '@coreui/icons';
import { IconSetService } from '@coreui/icons-angular';
import { GlobalService } from '../GlobalService';
import { HttpClient } from '@angular/common/http';



@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrl: './news.component.scss'
})




export class NewsComponent implements OnInit {


  
newsslides: any[] = [];
slides: any[] = [];
initialNewsData: any[]=[];
allNewsData: any[]=[];
allHeadlinesData: any[]=[];
initialHeadlinesData: any[]=[];
initialslides: any[]=[];
filterCriteria: string = '';
filterCriteriaWatcher: any;
filteredSlides: any[]=[];

constructor(public globalService: GlobalService ,private cd: ChangeDetectorRef, public iconSet: IconSetService,  private http: HttpClient)
{
 iconSet.icons = {cibTwitter,cibLinkedin,cibYoutube,cibFacebook,cilFolderOpen, cibCampaignMonitor ,cibInstagram,cilCash,cilFilterX,cilList,cilListFilter,cilTag,cilPuzzle,cilCart,cilWallet,cilStar,cilThumbUp,cilThumbDown,cilHeart,cilHandshake,cilSearch,cilFrown,cilHappy,cilMeh,cilArrowThickBottom,cilMediaRecord,cilReload,cilArrowBottom,cilArrowTop,cilMinus,cilMonitor,cilChartPie,cilOptions,cilCheckCircle, cilGlobeAlt, cilListNumbered, cilPaperPlane, cilBellExclamation, cilBookmark, cilCalendar , cilCalendarCheck, cilUser, cilWarning, cifUs, cifAd, cifBr, cifBs, cifBa, cifCa, cifBb,cifCd, cifAe,cifCh, cifCi, cifBj, cifCl,cifCm,cifCo,cifCr,cifAt,cifCu,cifCv, cifBw,cifCy,cifCz,cifDe,cifDj,cifDk,cifDm,cifDo,cifDz,cifEc,cifEe,cifEg,cifEr,cifEs,cifEt,cifFi,cifFj,cifFm,cifFr,cifId,cifIe,cifIl,cifIn,cifIq,cifIr,cifIs,cifIt,cifJm,cifJo,cifJp,cifKe,cifKg,cifKh,cifKi,cifKm,cifKn,cifKp,cifKr,cifKw,cifKz,cifGa,cifGb,cifGd,cifGe,cifGh,cifGm,cifGn,cifGq,cifGr,cifGt,cifGw,cifGy,cifHk,cifHn,cifHr,cifHt,cifHu,cifLa,cifLb,cifLc,cifLi,cifLk,cifLr,cifLs,cifLt, cifLu,cifLv,cifLy,cifMa,cifMc,cifMd,cifMe,cifMg,cifMh,cifMk,cifMl,cifMm,cifMn,cifMr,cifMt,cifMu,cifMv,cifMw,cifMx,cifMy,cifMz,cifNa,cifNe,cifNg,cifNi,cifNl,cifNo,cifNp,cifNr,cifNu,cifNz,cifOm,cifPa,cifPe,cifPg,cifPh,cifPk,cifPl,cifPt,cifPw,cifPy,cifQa,cifRo,cifRs,cifRu,cifRw,cifSa,cifSb,cifSc,cifSd,cifSe,cifSg,cifSi,cifSk,cifSl,cifSm,cifSn,cifSo,cifSr,cifSs,cifSt,cifSv,cifSy,cifSz,cifTd,cifTg,cifTh,cifTj,cifTl,cifTm,cifTn,cifTo,cifTr,cifTt,cifTv,cifTw,cifTz,cifUa,cifUg,cifUy,cifUz,cifVa,cifVc,cifVe,cifVn,cifWs,cifXk,cifYe,cifZa,cifZm,cifZw,cifAf,cifAg,cifAl,cifAm,cifAo,cifAr,cifAu,cifAz,cifBd,cifBe,cifBf,cifBg,cifBh,cifBi,cifBn,cifBo,cifBt,cifBy,cifBz,cifCf,cifCg,cifCn  }; 

   
}

  ngOnInit(): void {

     // news 
     this.globalService.retrieveall('News','News').subscribe((responseData: any) => {
     
      this.allNewsData = responseData;
      this.initialNewsData = responseData;
      console.log(" all News Data",this.allNewsData );

      const sortedNews = this.allNewsData.sort((a, b) => {
        return new Date(b.fields.publishedAt).getTime() - new Date(a.fields.publishedAt).getTime();
      });
      
      const latestNews = sortedNews.slice(0, 5);
      console.log("Latest News", latestNews);

      latestNews.forEach(async (newsItem,index) => {

        this.newsslides.push({
          id: index,
          src:'./assets/img/news/13.jpg', 
          title: newsItem.fields['title'], 
          subtitle: newsItem.fields['description'],
          source: newsItem.fields['source.name'], 
          url :newsItem.fields['url'] 
        });
      });
      console.log("newsslides 1 ", this.newsslides);
      this.cd.detectChanges(); // Force change detection


    },
    (error: any) => {
      console.log(error);
    }
    );

    //headlines

    this.globalService.retrieveall('Headlines','Headlines').subscribe((responseData: any) => {
     
      this.allHeadlinesData = responseData;
      this.initialHeadlinesData = responseData;
      console.log(" all Headlines Data",this.allHeadlinesData );
  
      const sortedNews = this.allHeadlinesData.sort((a, b) => {
        return new Date(b.fields.PublishedAt).getTime() - new Date(a.fields.PublishedAt).getTime();
      });
      
      const latestNews = sortedNews.slice(0, 5);
      console.log("Latest News", latestNews);
      this.slides = this.newsslides;
      this.initialslides = this.newsslides;
      latestNews.forEach(async (newsItem,index) => {
  
        this.slides.push({
          id: this.slides.length,
          src:'./assets/img/news/13.jpg', 
          title: newsItem.fields['title'], 
          source: newsItem.fields['source.name'], 
          url :newsItem.fields['url'] 
        });

      });
      this.cd.detectChanges(); // Force change detection

      console.log("slides 2 ", this.slides);
      console.log("initialslides 2 ", this.initialslides);

    
    },
    (error: any) => {
      console.log(error);
    }
    );
  
  }


// // Filter method
// getfilteredSlides(): void {

//   // Reset slides to the initial unfiltered array
//   this.slides = [...this.initialslides];

//   console.log("still filtred data ", this.slides);

//   // Apply the filter and update the slides
//   this.slides = this.slides.filter(slide => {
//     return (
//       this.filterCriteria && this.filterCriteria!== '' ? slide.title.toLowerCase().includes(this.filterCriteria.toLowerCase()) ||
//       (slide.subtitle && slide.subtitle.toLowerCase().includes(this.filterCriteria.toLowerCase())) : true
//     );

//   });

//   console.log(" filtred data ", this.slides);

//   this.cd.detectChanges(); 

// }

getfilteredSlides(): void {
  // Reset slides to the initial unfiltered array
  this.filteredSlides = [...this.initialslides];

  // Apply the filter
  if (this.filterCriteria !== '') {
    this.filteredSlides = this.slides.filter(slide => 
      slide.title.toLowerCase().includes(this.filterCriteria.toLowerCase())
    );
  }

  // Mark the component as needing to be checked for changes
  this.cd.markForCheck();
  this.cd.detectChanges();
}



}
