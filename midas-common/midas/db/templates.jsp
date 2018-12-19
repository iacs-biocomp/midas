<%@ taglib prefix="s" uri="/struts-tags" %>
	
		<link href="/css/entypo.css" rel="stylesheet">
 		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="/css/mouldifi-core.css" rel="stylesheet">
		<link href="/css/mouldifi-forms.css" rel="stylesheet"> 
		<link href="/css/midas.css" rel="stylesheet">
		<link href="/css/bigan.css" rel="stylesheet"> 
		
		<link href="/css/plugins/datatables/jquery.dataTables.css" rel="stylesheet">
		<link href="js/plugins/datatables/extensions/Buttons/css/buttons.dataTables.css" rel="stylesheet">	
		
        <style>
            .des-header {
                border-style: solid;
                border-width: medium;
                background-color: lightblue;
            }

            .des-frame {
                border-style: solid;
                border-width: medium;
                background-color: #78c578;
				max-height: 120px;                
            }

            .des-frame2 {
                border-style: solid;
                border-width: medium;
                background-color: #78c578;
				max-height: 40px;                
            }

            .template-item {
                border-style: solid;
                border-width: 5px;
                border-color: #fff;
                height: 170px;
            }

            .template-item:hover {
                border-style: solid;
                border-width: 5px;
                border-color: red;
            }
            
            .row {
                .margin-left: 0;
                .margin-right: 0;                
            }

.hidable {
    position: relative;
    min-height: 1px;
    padding-right: 2px;
    padding-left: 2px;
}
        </style>

	</head>


<!-- Page container -->
<div class="page-container height100 sidebar-collapsed">

  <!-- Main container -->
  <div class="main-container gray-bg height100">
  

		<!-- Main content -->
		<div class="main-content height100">

            <div class="col-lg-3" sytle="border-style: solid; border-width: medium;">
            </div>

			<!--  template list -->
            <div class="col-lg-9" sytle="border-style: solid; border-width: medium;">
               
               
<s:iterator value="templates" >	               
 				 <!-- template item -->
                 <div class="col-lg-2 template-item" onClick="alert('seleccionado bigan1')">

			        <div style="text-align: center;" class=""><span><s:property value="name"/></span></div>
			        
			        <!--  frame content -->
					<s:property escape="false" value="content"/>
			        <!--  /frame content -->

                </div>  
 				<!-- /template item -->
</s:iterator>

            </div>  		 
			<!--  /template list -->

		
	  </div>
	  <!-- /main content -->
	  
  </div>
  <!-- /main container -->
  
</div>
<!-- /page container -->
		            

