<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="utf-8">
    <title>JIRA Монитор</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="description" content="JIRA Монитор">
    <meta name="author" content="JIRA Монитор">

    <link href='http://fonts.googleapis.com/css?family=RobotoDraft:300,400,400italic,500,700' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300,400,400italic,600,700' rel='stylesheet' type='text/css'>

    <!--[if lt IE 10]>
    <script type="text/javascript" src="/js/media.match.min.js"></script>
    <script type="text/javascript" src="/js/placeholder.min.js"></script>
    <![endif]-->

    <link type="text/css" href="fonts/font-awesome/css/font-awesome.min.css" rel="stylesheet">        <!-- Font Awesome -->
    <link type="text/css" href="css/styles.css" rel="stylesheet">                                     <!-- Core CSS with all styles -->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries. Placeholdr.js enables the placeholder attribute -->
    <!--[if lt IE 9]>
    <link type="text/css" href="assets/css/ie8.css" rel="stylesheet">
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/respond.js/1.1.0/respond.min.js"></script>
    <script type="text/javascript" src="plugins/charts-flot/excanvas.min.js"></script>
    <script type="text/javascript" src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

</head>

<body class="infobar-offcanvas">


<div class="page-heading">
    <h1>Jira Monitor</h1>
    <div class="options">

    </div>
</div>
<div class="container-fluid">


    <div data-widget-group="group1">

        <div class="row">
            <div class="col-md-3">
                <div class="amazo-tile tile-success">
                    <div class="tile-heading">
                        <div class="title">Открытых\Закрытых обращений <b>за сегодня</b></div>

                    </div>
                    <div class="tile-body">
                        <div class="content"> ${issueNewToday}</br>
                            ${issueClosedToday}</div>
                    </div>
                    <div class="tile-footer">

                        <div id="sparkline-revenue" class="sparkline-line"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="amazo-tile tile-info" href="#">
                    <div class="tile-heading">
                        <div class="title">Кол-во <b>АКТИВНЫХ обращений</b> на данный момент</div>
                    </div>
                    <div class="tile-body">
                        <div class="content">
                            ${Levas}</br>
                            ${Esies}</br>
                            ${Kuaae}
                        </div>
                    </div>
                    <div class="tile-footer">
                        <span class="info-text text-right"></span>
                        <div class="progress">
                            <div class="progress-bar" style="width: 82%"></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="amazo-tile tile-white">
                    <div class="tile-heading">
                        <div class="title">Кол-во <b>ЗАКРЫТЫХ обращений</b> за текущий месяц</div>

                    </div>
                    <div class="tile-body">
						<span class="content">
						${LevasС}</br>${EsiesС}</br>${KuaaeС}</span>
                    </div>
                    <div class="tile-footer text-center">
                        <span class="info-text text-right" style="color: #f04743"></span>
                        <div id="sparkline-item" class="sparkline-bar"></div>
                    </div>
                </div>
            </div>



            <div class="col-md-3">
                <div class="amazo-tile tile-white">
                    <div class="tile-heading">
                        <span class="title">Лидер по кол-ву решенных заявок за ПРОШЛЫЙ месяц</span>
                        <span class="secondary">${Month}</span>
                    </div>
                    <div class="tile-body">
                        <span class="content">${Max}</span>
                    </div>
                    <div class="tile-footer">
                        <span class="info-text text-right" style="color: #94c355"></span>
                        <div id="sparkline-commission" class="sparkline"></div>
                    </div>
                </div>
            </div>

        </div>
        </br>

        <div class="col-md-3">
            <div class="amazo-tile tile-info" href="#">
                <div class="tile-heading">
                    <div class="title">Кол-во <b>ЗАКРЫТЫХ обращений</b> за тек.месяц</div>
                    <span class="secondary">${issueOpenCurmonth}</br>${issueClosedCurmonth}</span>
                </div>
                <div class="tile-body">
                    <div class="content">${defectClosedMonth}</br>${consultClosedMonth}</br>${whishClosedMonth}</div>
                </div>
                <div class="tile-footer">
                    <span class="info-text text-right"></span>
                    <div class="progress">
                        <div class="progress-bar" style="width: 82%"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="amazo-tile tile-info" href="#">
                <div class="tile-heading">
                    <div class="title">Кол-во <b>ОТКРЫТЫХ обращений</b> по категориям</div>
                    <span class="secondary">${issueOpenCurmonth}</br>${issueClosedCurmonth}</span>
                </div>
                <div class="tile-body">
                    <div class="content">${defectOpenNow}</br>${consultOpenNow}</div>
                </div>
                <div class="tile-footer">
                    <span class="info-text text-right"></span>
                    <div class="progress">
                        <div class="progress-bar" style="width: 82%"></div>
                    </div>
                </div>
            </div>
        </div>


        <!-- Load site level scripts -->
        <script type="text/javascript" src="js/jquery-1.10.2.min.js"></script> 							<!-- Load jQuery -->
        <script type="text/javascript" src="js/jqueryui-1.9.2.min.js"></script> 							<!-- Load jQueryUI -->
        <script type="text/javascript" src="js/bootstrap.min.js"></script> 								<!-- Load Bootstrap -->
        <script type="text/javascript" src="plugins/sparklines/jquery.sparklines.min.js"></script>  		<!-- Sparkline -->
        <script type="text/javascript" src="js/enquire.min.js"></script> 									<!-- Enquire for Responsiveness -->
        <!-- End loading site level scripts -->

        <!-- Load page level scripts-->

        <script type="text/javascript" src="plugins/wijets/wijets.js"></script>     								<!-- Wijet -->
        <script type="text/javascript" src="plugins/charts-chartistjs/chartist.min.js"></script>               	<!-- Chartist -->
        <script type="text/javascript" src="plugins/charts-chartistjs/chartist-plugin-tooltip.js"></script>    	<!-- Chartist -->
        <script type="text/javascript" src="js/demo-index.js"></script> 										<!-- Initialize scripts for this page-->

        <!-- End loading page level scripts-->
</body>
</html>