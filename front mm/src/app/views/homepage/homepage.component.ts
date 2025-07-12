import {  ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { cibCampaignMonitor, cibFacebook, cibInstagram, cibLinkedin, cibTwitter, cibYoutube, cifAd, cifAe, cifAf, cifAg, cifAl, cifAm, cifAo, cifAr, cifAt, cifAu, cifAz, cifBa, cifBb, cifBd, cifBe, cifBf, cifBg, cifBh, cifBi, cifBj, cifBn, cifBo, cifBr, cifBs, cifBt, cifBw, cifBy, cifBz, cifCa, cifCd, cifCf, cifCg, cifCh, cifCi, cifCl, cifCm, cifCn, cifCo, cifCr, cifCu, cifCv, cifCy, cifCz, cifDe, cifDj, cifDk, cifDm, cifDo, cifDz, cifEc, cifEe, cifEg, cifEr, cifEs, cifEt, cifFi, cifFj, cifFm, cifFr, cifGa, cifGb, cifGd, cifGe, cifGh, cifGm, cifGn, cifGq, cifGr, cifGt, cifGw, cifGy, cifHk, cifHn, cifHr, cifHt, cifHu, cifId, cifIe, cifIl, cifIn, cifIq, cifIr, cifIs, cifIt, cifJm, cifJo, cifJp, cifKe, cifKg, cifKh, cifKi, cifKm, cifKn, cifKp, cifKr, cifKw, cifKz, cifLa, cifLb, cifLc, cifLi, cifLk, cifLr, cifLs, cifLt, cifLu, cifLv, cifLy, cifMa, cifMc, cifMd, cifMe, cifMg, cifMh, cifMk, cifMl, cifMm, cifMn, cifMr, cifMt, cifMu, cifMv, cifMw, cifMx, cifMy, cifMz, cifNa, cifNe, cifNg, cifNi, cifNl, cifNo, cifNp, cifNr, cifNu, cifNz, cifOm, cifPa, cifPe, cifPg, cifPh, cifPk, cifPl, cifPt, cifPw, cifPy, cifQa, cifRo, cifRs, cifRu, cifRw, cifSa, cifSb, cifSc, cifSd, cifSe, cifSg, cifSi, cifSk, cifSl, cifSm, cifSn, cifSo, cifSr, cifSs, cifSt, cifSv, cifSy, cifSz, cifTd, cifTg, cifTh, cifTj, cifTl, cifTm, cifTn, cifTo, cifTr, cifTt, cifTv, cifTw, cifTz, cifUa, cifUg, cifUs, cifUy, cifUz, cifVa, cifVc, cifVe, cifVn, cifWs, cifXk, cifYe, cifZa, cifZm, cifZw, cilArrowBottom, cilArrowThickBottom, cilArrowTop, cilBellExclamation, cilBookmark, cilCalendar, cilCalendarCheck, cilCart, cilCash, cilChartPie, cilCheckCircle, cilFilterX, cilFolderOpen, cilFrown, cilGlobeAlt, cilHandshake, cilHappy, cilHeart, cilList, cilListFilter, cilListNumbered, cilMediaRecord, cilMeh, cilMinus, cilMonitor, cilOptions, cilPaperPlane, cilPuzzle, cilReload, cilSearch, cilStar, cilTag, cilThumbDown, cilThumbUp, cilUser, cilWallet, cilWarning } from '@coreui/icons';
import { IconSetService } from '@coreui/icons-angular';
import { GlobalService } from '../GlobalService';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import * as am5index from "@amcharts/amcharts5/index";
import am5geodata_worldLow from "@amcharts/amcharts5-geodata/worldLow";
import am5themes_Animated from "@amcharts/amcharts5/themes/Animated";
import * as am5 from "@amcharts/amcharts5";
import * as am5map from "@amcharts/amcharts5/map";
import am4geodata_worldLow from "@amcharts/amcharts4-geodata/worldLow";


@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.scss'
})
export class HomepageComponent implements OnInit{

  finalProductsReviews: any[] =[];
  initialProductsReviewsData: any[] =[];



  allOrdersData: any[]=[];
  initialOrdersData: any[]=[];
  allSalesData: any[]=[];
  initialSalesData: any[]=[];
  allShipmentsData: any[]=[];
  initialShipmentsData: any[]=[];
  allProductSKUData: any[]=[];
  initialProductSKUData: any[]=[];
  allSuppliersData: any[]=[];
  initialSuppliersData: any[]=[];
  allCategoriesData: any[]=[];
  initialCategoriesData: any[]=[];
  allReviewsData: any[]=[];
  initialReviewsData: any[]=[];
  finalProductSKUs: any[]=[];
  finalSales: any[]=[];
  finalReviews: any[]=[];
  allAttributeValuesData: any[]=[];
  initialAttributeValuesData: any[]=[];
  allAttributesData:any[]=[];
  initialAttributesData: any[]=[];
  allClientsData:any[]=[];
  initialClientsData:any[]=[];
  finalAttributeValues: any[]=[];
  allProductsData: any[]=[];
  initialProductsData: any[]=[];
  finalOrders: any[]=[];
  finalCategories: any[]=[];
  finalProducts: any[]=[];
   Ranking : any[] =[];
   readonlyRate: boolean = true; 
  currentPosition : number = 0;
  itemsPerPageTeam = 5; 
  itemsPerPage = 5; 
  currentPage = 1;
  currentPageTeam = 1;
  allHeadlinesData: any[]=[];
  initialHeadlinesData: any[]=[];
  initialNewsData: any[]=[];
  allNewsData: any[]=[];
  allFactTableData: any[]=[];
  initialFactTableData: any[]=[];
  finalFactTable: any[]=[];
  allAdsData: any[]=[];
  initialAdsData: any[]=[];
  allcampaignsData:  any[]=[];
  initialcampaignsData:  any[]=[];
  allTeamsData:  any[]=[];
  initialTeamsData: any[]=[];
  allTeamMembersData:  any[]=[];
  initialTeamMembersData:  any[]=[];
  allTeamData:  any[]=[];
  allaccountsData: any[]=[];
  initialaccountsData:  any[]=[];
  finalTeams: any[]=[];
  finalAds: any[]=[];
  imgUrl: string = './assets/img/news/3.jpg'; // Default avatar URL
  uniqueShipmentstatus: any[]=[];
  ShipmentStatusCount: any[]=[];
  ShipmentTypesCount: any[]=[];
  UniqueShipmentTypes: any[]=[];
  UniqueShipmentLocations: any[]=[];
  totalRevenue: any;
  averageRevenue: any;
  mappedKPIs: any;
  totalQualifiedLeads: number =0;
  RepeatPurchaseRate: number =0;
  Client_LifeSpan: number=0;
  CLV: any;
  MRR: any;
  nbr_New_Resellers: number=0;
  nbr_views: number=0;
  nbr_reviews: number=0;
  nbr_likes: number=0;
  totalProfitCampaigns: number=0;
  nbr_unsatisfied_clients: number=0;
  ROI_sales: number=0;
  ROI_campaign: number=0;
  Gross_ProfitMargin: number=0;
  Net_ProfitMargin: number=0;
  nbr_Fulfilled_Orders: number=0;
  nbr_Returned_Orders: number=0;
  productionCapacity: number=0;
  nbr_purchasesByOrder: number=0;

  nbr_unsatisfied_resellers: number=0;



  constructor(public domSanitizer: DomSanitizer, public globalService: GlobalService ,private cd: ChangeDetectorRef, public iconSet: IconSetService,  private http: HttpClient)
   {
    iconSet.icons = {cibTwitter,cibLinkedin,cibYoutube,cibFacebook,cilFolderOpen, cibCampaignMonitor ,cibInstagram,cilCash,cilFilterX,cilList,cilListFilter,cilTag,cilPuzzle,cilCart,cilWallet,cilStar,cilThumbUp,cilThumbDown,cilHeart,cilHandshake,cilSearch,cilFrown,cilHappy,cilMeh,cilArrowThickBottom,cilMediaRecord,cilReload,cilArrowBottom,cilArrowTop,cilMinus,cilMonitor,cilChartPie,cilOptions,cilCheckCircle, cilGlobeAlt, cilListNumbered, cilPaperPlane, cilBellExclamation, cilBookmark, cilCalendar , cilCalendarCheck, cilUser, cilWarning, cifUs, cifAd, cifBr, cifBs, cifBa, cifCa, cifBb,cifCd, cifAe,cifCh, cifCi, cifBj, cifCl,cifCm,cifCo,cifCr,cifAt,cifCu,cifCv, cifBw,cifCy,cifCz,cifDe,cifDj,cifDk,cifDm,cifDo,cifDz,cifEc,cifEe,cifEg,cifEr,cifEs,cifEt,cifFi,cifFj,cifFm,cifFr,cifId,cifIe,cifIl,cifIn,cifIq,cifIr,cifIs,cifIt,cifJm,cifJo,cifJp,cifKe,cifKg,cifKh,cifKi,cifKm,cifKn,cifKp,cifKr,cifKw,cifKz,cifGa,cifGb,cifGd,cifGe,cifGh,cifGm,cifGn,cifGq,cifGr,cifGt,cifGw,cifGy,cifHk,cifHn,cifHr,cifHt,cifHu,cifLa,cifLb,cifLc,cifLi,cifLk,cifLr,cifLs,cifLt, cifLu,cifLv,cifLy,cifMa,cifMc,cifMd,cifMe,cifMg,cifMh,cifMk,cifMl,cifMm,cifMn,cifMr,cifMt,cifMu,cifMv,cifMw,cifMx,cifMy,cifMz,cifNa,cifNe,cifNg,cifNi,cifNl,cifNo,cifNp,cifNr,cifNu,cifNz,cifOm,cifPa,cifPe,cifPg,cifPh,cifPk,cifPl,cifPt,cifPw,cifPy,cifQa,cifRo,cifRs,cifRu,cifRw,cifSa,cifSb,cifSc,cifSd,cifSe,cifSg,cifSi,cifSk,cifSl,cifSm,cifSn,cifSo,cifSr,cifSs,cifSt,cifSv,cifSy,cifSz,cifTd,cifTg,cifTh,cifTj,cifTl,cifTm,cifTn,cifTo,cifTr,cifTt,cifTv,cifTw,cifTz,cifUa,cifUg,cifUy,cifUz,cifVa,cifVc,cifVe,cifVn,cifWs,cifXk,cifYe,cifZa,cifZm,cifZw,cifAf,cifAg,cifAl,cifAm,cifAo,cifAr,cifAu,cifAz,cifBd,cifBe,cifBf,cifBg,cifBh,cifBi,cifBn,cifBo,cifBt,cifBy,cifBz,cifCf,cifCg,cifCn  }; 

      
  }

  newsslides: any[] = [];
  slides: any[] = [];
  data: any;


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
      latestNews.forEach(async (newsItem,index) => {
  
        this.slides.push({
          id: this.slides.length,
          src:'./assets/img/news/13.jpg', 
          title: newsItem.fields['title'], 
          source: newsItem.fields['source.name'], 
          url :newsItem.fields['url'] 
        });
      });
      console.log("slides 2 ", this.slides);
  
      this.cd.detectChanges(); // Force change detection
  
    },
    (error: any) => {
      console.log(error);
    }
    );
  

    //Account
    this.globalService.retrieveall('Account','accounts').subscribe((responseData: any) => {
      this.allaccountsData = responseData;
      this.initialaccountsData = responseData;
      console.log(" all accounts Data",this.allaccountsData );
  
    
     },
       (error: any) => {
    console.log(error);
       }
      );
  
      //Campaign
      this.globalService.retrieveall('Campaign','campaigns').subscribe((responseData: any) => {
        this.allcampaignsData = responseData;
        this.initialcampaignsData = responseData;
        console.log(" all campaigns Data",this.allcampaignsData );
    
       },
         (error: any) => {
    console.log(error);
         }
       );

    //Categories
    this.globalService.retrieveall('Category','Categories').subscribe((responseData: any) => {
     
      this.allCategoriesData = responseData;
      this.initialCategoriesData = responseData;
      console.log(" all Categories Data",this.allCategoriesData );
      this.returnFinalCategories();
         // Suppliers
    this.globalService.retrieveall('Supplier','Suppliers').subscribe((responseData: any) => {
     
      this.allSuppliersData = responseData;
      this.initialSuppliersData = responseData;
      console.log(" all Suppliers Data",this.allSuppliersData );
    },
    (error: any) => {
      console.log(error);
    }
    );  

       // Shipments
       this.globalService.retrieveall('Shipment','Shipments').subscribe((responseData: any) => {
     
        this.allShipmentsData = responseData;
        this.initialShipmentsData = responseData;
        console.log(" all Shipments Data",this.allShipmentsData );
      },
      (error: any) => {
        console.log(error);
      }
      );  

       // Client
       this.globalService.retrieveall('Clients','Clients').subscribe((responseData: any) => {
     
         this.allClientsData = responseData;
         this.initialClientsData = responseData;
                 console.log(" all Clients  Data",this.allClientsData );
              },
              (error: any) => {
                console.log(error);
              }
               );

                 // Product
      this.globalService.retrieveall('Product','Products').subscribe((responseData: any) => {
     
        this.allProductsData = responseData;
        this.initialProductsData = responseData;
         console.log(" all Products  Data",this.allProductsData );
         this. returnFinalProducts();

           // Attributes 
        this.globalService.retrieveall('Property','Properties').subscribe((responseData: any) => {
     
          this.allAttributesData = responseData;
          this.initialAttributesData = responseData;
           console.log(" all Attributes  Data",this.allAttributesData );

             // Attribute Values
      this.globalService.retrieveall('PropertyValue','PropertyValues').subscribe((responseData: any) => {
     
        this.allAttributeValuesData = responseData;
        this.initialAttributeValuesData = responseData;
         console.log(" all Attribute Values Data",this.allAttributeValuesData );
         this.returnFinalAttributeValues();
      },
      (error: any) => {
        console.log(error);
      }
       );
// ProductSKU
this.globalService.retrieveall('ProductSKU','ProductSKU').subscribe((responseData: any) => {
     
  this.allProductSKUData = responseData;
  this.initialProductSKUData = responseData;
  console.log(" all ProductSKU Data",this.allProductSKUData );
  this.returnFinalProductSKUs();

      //TeamMember
      this.globalService.retrieveall('TeamMember','TeamMembers').subscribe((responseData: any) => {
        this.allTeamMembersData = responseData;
        this.initialTeamMembersData = responseData;
        //Team
        this.globalService.retrieveall('Team','Teams').subscribe((responseData: any) => {
          this.allTeamsData = responseData;
          this.initialTeamsData = responseData;
          console.log(" all Teams Data",this.allTeamsData );
         
          this.returnFinalTeams();
  
          //Ad
          this.globalService.retrieveall('Ad','Ads').subscribe((responseData: any) => {
            this.allAdsData = responseData;
            this.initialAdsData = responseData;
            console.log(" all Ads Data",this.allAdsData );
            this.returnFinalAds();
      
           },
             (error: any) => {
       console.log(error);
             }
         );
         },
           (error: any) => {
        console.log(error);
           }
           );
          
        console.log(" all TeamMembers Data",this.allTeamMembersData );
      
       },
         (error: any) => {
      console.log(error);
         }
        );
   
    // Reviews
    this.globalService.retrieveall('Review','Reviews').subscribe((responseData: any) => {
     
      this.allReviewsData = responseData;
      this.initialReviewsData = responseData;
      console.log(" all Reviews Data",this.allReviewsData );
      this. returnFinalReviews();
    },
    (error: any) => {
      console.log(error);
    }
    );
     // Orders
     this.globalService.retrieveall('Order','Orders').subscribe((responseData: any) => {
     
      this.allOrdersData = responseData;
      this.initialOrdersData = responseData;
      console.log(" all Orders Data",this.allOrdersData );
      this.returnFinalOrders();
      // Sales
    this.globalService.retrieveall('Sales','Sales').subscribe((responseData: any) => {
     
      this.allSalesData = responseData;
      this.initialSalesData = responseData;
      console.log(" all Sales Data",this.allSalesData );
      this.  returnFinalSales();
    },
    (error: any) => {
      console.log(error);
    }
    );   
    },
    (error: any) => {
      console.log(error);
    }
    );   


  
   
  

},
(error: any) => {
  console.log(error);
}
); 

        },
        (error: any) => {
          console.log(error);
        }
         );
      },
      (error: any) => {
        console.log(error);
      }
       );
 
    },
    (error: any) => {
      console.log(error);
    }
    );

    this.globalService.retrieveall('FactTable','FactTable').subscribe((responseData: any) => {
       this.allFactTableData = responseData;
       this.initialFactTableData = responseData;
       console.log(" all FactTable Data",this.allFactTableData );
       this.returnFinalFactTable();
       this.Calculate();
       this.map();

      },
        (error: any) => {
  console.log(error);
        }
    );
  
  }

  returnFinalCategories(){
    const enrichedCategories: any[] = [];
    const categoryMap: { [id: string]: any } = {};
  
    this.allCategoriesData.forEach((category) => {
      categoryMap[category.id] = category;
    });
  
    for (let category of this.allCategoriesData) {
      let parent: any;
      const parentId = category.fields.parent_id;
  
      if (parentId === 'NaN' ||!parentId) {
        parent = null;
      } else {
        parent = categoryMap[parentId];
      }
  
        const enrichedCategory = {
         ...category,
          parent
        };
        enrichedCategories.push(enrichedCategory);
      
    }
    this.finalCategories = enrichedCategories;

    console.log("enrichedCategories", enrichedCategories);
    return enrichedCategories;
  }
  returnFinalProductSKUs(){
    const enrichedSKUs: any[] = [];
    const ProductMap: { [id: string]: any } = {};
    const AttributeMap: { [id: string]: any } = {};

    this.finalProducts.forEach((prod) => {
      ProductMap[prod.id] = prod;
    });
    this.allAttributesData.forEach((atr) => {
      AttributeMap[atr.id] = atr;
    });

    for (const sku of this.allProductSKUData )
      {
        let product ;
        let attribute;
        let finalAttributes: any[] = [];

        const prodId = sku.fields.product_key;
        const attributesId = sku.fields.attributes_key;

        product = ProductMap[prodId];

        for (const atrId of attributesId) { 

          attribute = AttributeMap[atrId];
          if (attribute) { 
            finalAttributes.push(attribute);
          } else {
            // console.log(`Order not found: ${atrId}`);
          }
        }
        const enrichedSKU = {
          ...sku,
          product,
          finalAttributes
         };
         enrichedSKUs.push(enrichedSKU);

      }
      this.finalProductSKUs = enrichedSKUs;
      console.log("enrichedSKUs", enrichedSKUs);
      return enrichedSKUs;
    
  }
  returnFinalOrders(){
    const enrichedOrders: any[] = [];

    const SKUMap: { [id: string]: any } = {};
    const ShipmentMap: { [id: string]: any } = {};
    const ClientMap: { [id: string]: any } = {};
    const SupplierMap: { [id: string]: any } = {};

    this.finalProductSKUs.forEach((atr) => {
      SKUMap[atr.id] = atr;
    });
    this.allShipmentsData.forEach((p) => {
      ShipmentMap[p.id] = p;
    });
    this.allClientsData.forEach((atr) => {
      ClientMap[atr.id] = atr;
    });
    this.allSuppliersData.forEach((p) => {
      SupplierMap[p.id] = p;
    });
    for (const order of this.allOrdersData )
      {
        let sku ;
        let shipment;
        let client ;
        let supplier;

        const skuId = order.fields.productSKU_key;
        sku = SKUMap[skuId];

        const shipId = order.fields.shipment_key;
        shipment = ShipmentMap[shipId];

        const cltId = order.fields.client_key;
        client = ClientMap[cltId];

        const supId = order.fields.supplier_key;
        supplier = SupplierMap[supId];

        const enrichedOrder = {
          ...order,
          sku,
          shipment,
          client,
          supplier
         };
         enrichedOrders.push(enrichedOrder);

      }
      this.finalOrders = enrichedOrders;
      console.log("enrichedOrders", enrichedOrders);
      return enrichedOrders;
    
  }
  returnFinalAttributeValues(){
    const enrichedAttributeValues: any[] = [];
    const AttributeMap: { [id: string]: any } = {};
    const ProductMap: { [id: string]: any } = {};

    this.allAttributesData.forEach((atr) => {
      AttributeMap[atr.id] = atr;
    });
    this.finalProducts.forEach((p) => {
      ProductMap[p.id] = p;
    });
    for (const val of this.allAttributeValuesData )
      {
        let attribute ;
        let product;
        const atrId = val.fields.attribute_key;
        attribute = AttributeMap[atrId];

        const productId = val.fields.product_key;
        product = ProductMap[productId];

        const enrichedAttributeValue = {
          ...val,
          attribute,
          product
         };
         enrichedAttributeValues.push(enrichedAttributeValue);

      }
      this.finalAttributeValues = enrichedAttributeValues;
      console.log("enrichedAttributeValues", enrichedAttributeValues);
      return enrichedAttributeValues;
    
    
  }
  returnFinalReviews(){
    const enrichedReviews: any[] = [];
    const SKUMap: { [id: string]: any } = {};
    this.finalProductSKUs.forEach((sku) => {
      SKUMap[sku.id] = sku;
    });
    for (const review of this.allReviewsData )
      {
        let productSKU ;
        const skuId = review.fields.productSKU_key;
        productSKU = SKUMap[skuId];
        const enrichedReview = {
          ...review,
          productSKU
         };
         enrichedReviews.push(enrichedReview);

      }
      this.finalReviews = enrichedReviews;
      this.finalProductsReviews = this.finalReviews.filter(review => review.productSKU);

      this.initialProductsReviewsData =  this.finalReviews.filter(review => review.productSKU);



      console.log("enrichedReviews", enrichedReviews);
      return enrichedReviews;
    
  }
  returnFinalSales(){
    const enrichedSales: any[] = [];
    const SKUMap: { [id: string]: any } = {};
    const OrderMap: { [id: string]: any } = {};

    this.finalProductSKUs.forEach((sku) => {
      SKUMap[sku.id] = sku;
    });
    this.finalOrders.forEach((order) => {
      OrderMap[order.id] = order;
    });

    for (const sale of this.allSalesData )
      {
        let productSKU ;
        let order;
        let finalOrders: any[] = [];
        const skuId = sale.fields.productSKU_key;
        const ordersId = sale.fields.orders_key;
        productSKU = SKUMap[skuId];

        for (const orderId of ordersId) {
           order = OrderMap[orderId];
          if (order) { 
            finalOrders.push(order);
          } else {
            // console.log(`Order not found: ${orderId}`);
          }
        }
        const enrichedSale = {
          ...sale,
          productSKU,
          finalOrders
         };
         enrichedSales.push(enrichedSale);

      }
      this.finalSales = enrichedSales;
      console.log("enrichedSales", enrichedSales);
      return enrichedSales;

  }
  returnFinalProducts(){
  const enrichedProducts: any[] = [];
  const categoryMap: { [id: string]: any } = {};
  
  this.finalCategories.forEach((category) => {
    categoryMap[category.id] = category;
  });

  for (const product of this.allProductsData )
    {
      let category;
      const catId = product.fields.category_id;
      category = categoryMap[catId];
      const enrichedProduct = {
        ...product,
        category
       };
       enrichedProducts.push(enrichedProduct);

    }
    this.finalProducts= enrichedProducts;

    console.log("enrichedProducts", enrichedProducts);
    return enrichedProducts;
  }
 
  returnFinalTeams() {
  const enrichedTeams: any[] = [];
  const memberMap: { [id: string]: any } = {};

  this.allTeamMembersData.forEach((member) => {
    memberMap[member.id] = member;
  });

  for (const team of this.allTeamsData) {
    const finalMembers: any[] = [];

    const memberIds = team.fields.TeamMembers;

    for (const memberId of memberIds) {
      const member = memberMap[memberId];
      if (member) {
        finalMembers.push(member);
      }
    }

    const enrichedTeam = {
      ...team,
      finalMembers
    };
    enrichedTeams.push(enrichedTeam);
  }

  this.finalTeams = enrichedTeams;
  console.log("enrichedTeams", enrichedTeams);
  return enrichedTeams;
  }

  returnFinalAds() {
  const enrichedAds: any[] = [];

  const teamMap: { [id: string]: any } = {};
  const skuMap: { [id: string]: any } = {};
  const campaignMap: { [id: string]: any } = {};
  const accountMap: { [id: string]: any } = {};

  // Populate maps for quick lookup
  this.finalTeams.forEach((team) => {
    teamMap[team.id] = team;
  });
  this.finalProductSKUs.forEach((sku) => {
    skuMap[sku.id] = sku;
  });
  this.allcampaignsData.forEach((campaign) => {
    campaignMap[campaign.id] = campaign;
  });
  this.allaccountsData.forEach((account) => {
    accountMap[account.id] = account;
  });

  // Process each ad
  for (const ad of this.allAdsData) {
    const skuId = ad.fields.productSKU_key;
    const teamId = ad.fields.CreativeTeam_id;
    const accountId = ad.fields.Account_id;
    const campaignId = ad.fields.Campaign_Id;

    const sku = skuMap[skuId];
    const team = teamMap[teamId];
    const account = accountMap[accountId];
    const campaign = campaignMap[campaignId];

    const enrichedAd = {
      ...ad,
      sku,
      team,
      account,
      campaign
    };

    enrichedAds.push(enrichedAd);
  }

  this.finalAds = enrichedAds;
  console.log("enrichedAds", enrichedAds);
  return enrichedAds;
  }

  returnFinalFactTable(){

  const enrichedFactTable: any[] = [];

  const SKUMap: { [id: string]: any } = {};
  const ShipmentMap: { [id: string]: any } = {};
  const ClientMap: { [id: string]: any } = {};
  const SupplierMap: { [id: string]: any } = {};
  const ReviewMap: { [id: string]: any } = {};
  const AdMap: { [id: string]: any } = {};
  const CampaignMap: { [id: string]: any } = {};
  const AccountMap: { [id: string]: any } = {};
  const TeamMap: { [id: string]: any } = {};
  const SaleMap: { [id: string]: any } = {};
  const OrderMap: { [id: string]: any } = {};



  this.finalOrders.forEach((atr) => {
    OrderMap[atr.id] = atr;
  });
  this.finalProductSKUs.forEach((atr) => {
    SKUMap[atr.id] = atr;
  });
  this.finalProductsReviews.forEach((atr) => {
    ReviewMap[atr.id] = atr;
  });
  this.finalAds.forEach((atr) => {
    AdMap[atr.id] = atr;
  });
  this.allcampaignsData.forEach((atr) => {
    CampaignMap[atr.id] = atr;
  });
  this.allaccountsData.forEach((atr) => {
    AccountMap[atr.id] = atr;
  });
  this.finalTeams.forEach((atr) => {
    TeamMap[atr.id] = atr;
  });
  this.finalSales.forEach((atr) => {
    SaleMap[atr.id] = atr;
  });
  this.allShipmentsData.forEach((p) => {
    ShipmentMap[p.id] = p;
  });
  this.allClientsData.forEach((atr) => {
    ClientMap[atr.id] = atr;
  });
  this.allSuppliersData.forEach((p) => {
    SupplierMap[p.id] = p;
  });


  for (const fact of this.allFactTableData )
    {
      let sku ;
      let shipment;
      let client ;
      let supplier;
      let sale;
      let ad;
      let order;
      let review;
      let campaign;
      let team;
      let account;


      const skuId = fact.fields.productSKU_key;
      sku = SKUMap[skuId];

      const shipId = fact.fields.shipment_key;
      shipment = ShipmentMap[shipId];

      const cltId = fact.fields.client_key;
      client = ClientMap[cltId];

      const supId = fact.fields.supplier_key;
      supplier = SupplierMap[supId];


      const accountId = fact.fields.account_key;
       account = AccountMap[accountId];

      const teamId = fact.fields.team_key;
       team = TeamMap[teamId];

      const campaignId = fact.fields.campaign_key;
       campaign = CampaignMap[campaignId];

       const saleId = fact.fields.sale_key;
       sale = SaleMap[saleId];


       const orderId = fact.fields.order_key;
       order = OrderMap[orderId];

       const reviewId = fact.fields.review_key;
       review = ReviewMap[reviewId];


       const adId = fact.fields.Ad_key;
       ad = AdMap[adId];



      const enrichedfact = {
        ...fact,
        sku,
        review,
        shipment,
        client,
        supplier,
        team,
        account,
        campaign,
        ad,
        order,
        sale,


       };
       enrichedFactTable.push(enrichedfact);

    }
    this.finalFactTable = enrichedFactTable;
    console.log("enrichedFactTable", enrichedFactTable);
    return enrichedFactTable;

  }

  // Function to count occurrences of each type
countShipmentTypes(data: any[]): number[] {
  const types = data.map(order => this.globalService.getInfo('type', order.shipment));
  const typeCounts = types.reduce((counts, type) => {
    counts[type] = (counts[type] || 0) + 1;
    return counts;
  }, {});

  return Object.values(typeCounts);
}
// Function to count occurrences of each status
countShipmentByKey(data: any[], key:string): number[] {
  const objects = data.map(order => this.globalService.getInfo(key, order.shipment));
  const objectsCounts = objects.reduce((counts, object) => {
    counts[object] = (counts[object] || 0) + 1;
    return counts;
  }, {});

  return Object.values(objectsCounts);
}

totalBykey(object: any,key:any): number{
  let previousobject: any = null;           
  let total : number =0;
  // console.log(" key : ",key , "object : ",object);

  // console.log("fact object ", this.finalFactTable[10][object]);
  // console.log("fact key ", this.finalFactTable[10].fields[key]);

  this.finalFactTable.forEach((fact)=> {
    if(fact[object])
   {  const currentobject = fact[object];

    if (previousobject !== currentobject) {
      total += fact.fields[key];
    }
    previousobject = currentobject;}
      else {
        console.log("00000000000000")
        total = 0; }    
  
  });

      return total;
}
total(object: any,key:any):number{
  let total : number =0;
  this.finalFactTable.forEach((fact)=> {

  if(fact[object])
    {  
      if (fact.fields[key])
 
      { total += fact.fields[key];}
     }
       else {
         console.log("00000000000000")
         total = 0; }    
   
   });
 
       return total;
}

 Calculate(){

  this.uniqueShipmentstatus = this.globalService.getUniqueOptions(this.finalOrders,'shipment','status');
  this.UniqueShipmentTypes = this.globalService.getUniqueOptions(this.finalOrders,'shipment','type');
  this.UniqueShipmentLocations= this.globalService.getUniqueOptions(this.finalOrders,'shipment','country');
  this.ShipmentStatusCount =this.countShipmentByKey(this.finalOrders,'status');
  this.ShipmentTypesCount =this.countShipmentByKey(this.finalOrders,'type'); 

  let previousValue: any = null;           
  this.allFactTableData.forEach((fact)=> {
    if(fact.fields.Total_Revenu)
   { const currentValue = fact.fields.Total_Revenu;
    if (previousValue !== currentValue) {
      this.totalRevenue += currentValue;
    }
      previousValue = currentValue;}
      else {this.totalRevenue = 0; }
  });

  this.averageRevenue = this.totalRevenue / this.allFactTableData.length;

  //client
  this.totalQualifiedLeads = this.total('client','nbr_QualifiedLeads');
  this.RepeatPurchaseRate = this.total('client','RepeatPurchaseRate');
  this.Client_LifeSpan = this.total('client','Client_LifeSpan');
  this.CLV = this.total('client','CLV') / this.allClientsData.length;
  this.MRR = this.total('client','MRR')  / this.allClientsData.length; 
  this.nbr_New_Resellers = this.total('client','nbr_New_Resellers');
  this.nbr_unsatisfied_clients = this.total('client','Unsatisfied_Clients');
  this.nbr_unsatisfied_resellers= this.total('client','Unsatisfied_Resellers');
 
  //campaign
  this.nbr_views = this.total('campaign','nbr_views');
  this.nbr_reviews = this.total('campaign','nbr_reviews');
  this.nbr_likes = this.total('campaign','nbr_likes');
  this.totalProfitCampaigns = this.totalBykey('campaign','TotalProfit_Campaign');
 

  

  //sales
  this.ROI_sales = this.totalBykey('campaign','ROI_sales');
  this.ROI_campaign = this.totalBykey('campaign','ROI_campaign');
  this.Gross_ProfitMargin = this.totalBykey('campaign','Gross_ProfitMargin');
  this.Net_ProfitMargin = this.totalBykey('campaign','Net_ProfitMargin');
  this.nbr_Fulfilled_Orders = this.totalBykey('order','nbr_Fulfilled_Orders');
  this.nbr_Returned_Orders = this.totalBykey('order','nbr_Returned_Orders');
  this.productionCapacity = this.totalBykey('order','productionCapacity');
  this.nbr_purchasesByOrder = this.totalBykey('order','nbr_purchasesByOrder')/this.allOrdersData.length;




  console.log("Total Revenue:", this.totalRevenue);
  console.log("Average Revenue:", this.averageRevenue);
  console.log("ShipmentStatusCount:", this.ShipmentStatusCount);
  console.log("ShipmentTypesCount:", this.ShipmentTypesCount);
  console.log("totalQualifiedLeads:", this.totalQualifiedLeads);

  console.log(" ROI_sales:", this.ROI_sales);
  console.log(" ROI_campaign:", this.ROI_campaign);
  console.log(" Gross_ProfitMargin:", this.Gross_ProfitMargin);
  console.log(" Net_ProfitMargin:", this.Net_ProfitMargin);
  console.log(" nbr_Fulfilled_Orders:", this.nbr_Fulfilled_Orders);
  console.log(" nbr_Returned_Orders:", this.nbr_Returned_Orders);
  console.log(" productionCapacity:", this.productionCapacity);



 }


map() {
  const uniqueShipments = this.finalFactTable.filter((value, index, self) =>
    index === self.findIndex((t) => t.shipment === value.shipment)
  );

  const extractedData = uniqueShipments.map(item => ({
    shipment: item.shipment,
    availability: item.fields.availability,
    activeClients: item.fields.activeClients,
    country: item.shipment.fields.country,
  }));

  console.log("extractedData", extractedData);

  const countrySummedData = extractedData.reduce((acc, current) => {
    const country = current.country as string;

    if (!(acc as any)[country]) {
      (acc as any)[country] = {
        country: current.country,
        totalAvailability: current.availability,
        totalActiveClients: current.activeClients,
      };
    } else {
      (acc as any)[country].totalAvailability += current.availability;
      (acc as any)[country].totalActiveClients += current.activeClients;
    }

    return acc;
  }, {});

  const summedDataArray = Object.values(countrySummedData)as Array<{
    country: string;
    totalAvailability: number;
    totalActiveClients: number;
  }>;

  console.log("summedDataArray", summedDataArray);

  /* Chart code */
  let root = am5.Root.new("mapdiv");
  root.setThemes([am5themes_Animated.new(root)]);

  let chart = root.container.children.push(am5map.MapChart.new(root, {}));

  let polygonSeries = chart.series.push(
    am5map.MapPolygonSeries.new(root, {
      geoJSON: am5geodata_worldLow,
      exclude: ["AQ"]
    })
  );

  let bubbleSeries = chart.series.push(
    am5map.MapPointSeries.new(root, {
      valueField: "value",
      calculateAggregates: true,
      polygonIdField: "id"
    })
  );

  let circleTemplate = am5.Template.new({});

  bubbleSeries.bullets.push(function(root, series, dataItem) {
    let container = am5.Container.new(root, {});

    let circle = container.children.push(
      am5.Circle.new(root, {
        radius: 20,
        fillOpacity: 0.7,
        fill: am5.color(0xff0000),
        cursorOverStyle: "pointer",
        tooltipText: "{name}: [bold]\nAvailability: {value}[/]\nActive Clients: {activeClients}"
      })
    );

    circle.setAll({
      radius: 20,
      fillOpacity: 0.5,
      fill: am5.color(0xff0000),
      cursorOverStyle: "pointer",
      tooltipText: "{name}: [bold]\nAvailability: {value}[/]\nActive Clients: {activeClients}"
    });

    let countryLabel = container.children.push(
      am5.Label.new(root, {
        text: "{name}",
        paddingLeft: 5,
        populateText: true,
        fontWeight: "bold",
        fontSize: 13,
        centerY: am5.p50
      })
    );

    circle.on("radius", function(radius) {
      countryLabel.set("x", radius);
    });

    return am5.Bullet.new(root, {
      sprite: container,
      dynamic: true
    });
  });

  bubbleSeries.bullets.push(function(root, series, dataItem) {
    return am5.Bullet.new(root, {
      sprite: am5.Label.new(root, {
        text: "{value.formatNumber('#.')}",
        fill: am5.color(0xffffff),
        populateText: true,
        centerX: am5.p50,
        centerY: am5.p50,
        textAlign: "center"
      }),
      dynamic: true
    });
  });

  bubbleSeries.set("heatRules", [
    {
      target: circleTemplate,
      dataField: "value",
      min: 10,
      max: 50,
      minValue: 0,
      maxValue: 1000,
      key: "radius"
    }
  ]);

  bubbleSeries.data.setAll(summedDataArray.map((item : any ) => ({
    id: this.globalService.loadCountries( item.country), 
    name: item.country,
    value: item.totalAvailability ,
    activeClients: item.totalActiveClients, 

  })));

  updateData();
  setInterval(updateData, 2000);

  function updateData() {
    for (let i = 0; i < bubbleSeries.dataItems.length; i++) {
      if (i < summedDataArray.length) {
        bubbleSeries.data.setIndex(i, {
          value: summedDataArray[i].totalAvailability, 
          id: summedDataArray[i].country, 
          name: summedDataArray[i].country,
          activeClients: summedDataArray[i].totalActiveClients,
        });
      }
    }
  }
}


}
