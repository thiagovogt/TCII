<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VSLSystem - Visualizer of Scientific Collaborations</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/main.css" />" />
  <link href="http://visjs.org/dist/vis.css" rel="stylesheet" type="text/css" />
  <script src="http://visjs.org/dist/vis.js"></script>
  
  
  <style>
    body {
      font: 10pt arial;
    }
    #mynetwork {
      width: 1000px;
      height: 600px;
      border: 1px solid lightgray;
      background: #F3F3F3;
    }
  </style>

  <script type="text/javascript">
    var DIR = '/VSLSystem/resources/img/soft-scraps-icons/';
    var nodes = null;
    var edges = null;
    var network = null;
    function draw() {
      // create people
      nodes = [
        {id: 1,  label: 'Sabrina Marczak',  image: DIR + 'User-Executive-Green-icon.png', shape: 'image'},
        {id: 2,  label: 'Technologies to Support Collaboration across Time Zones.',  image: DIR + 'Document-icon48.png', shape: 'image'},
        {id: 3,  label: 'Trust in virtual teams: theory and tools.',    image: DIR + 'Document-icon48.png', shape: 'image'},
        {id: 4,  label: 'Ban Al-Ani',    image: DIR + 'User-Administrator-Blue-icon.png', shape: 'image'},
        {id: 5,  label: 'David F. Redmiles',    image: DIR + 'User-Administrator-Blue-icon.png', shape: 'image'},
        {id: 6,  label: 'Cleidson R. B. de Souza',    image: DIR + 'User-Administrator-Blue-icon.png', shape: 'image'},
        {id: 7,  label: 'Rafael Prikladnicki',    image: DIR + 'User-Administrator-Blue-icon.png', shape: 'image'},
        {id: 8,  label: 'Filippo Lanubile',    image: DIR + 'User-Administrator-Blue-icon.png', shape: 'image'},
        {id: 9,  label: 'Fabio Calefato',    image: DIR + 'User-Administrator-Blue-icon.png', shape: 'image'}
      ];
      // create connections
      var color = '#BFBFBF';
      edges = [
        {from: 1,   to: 2, value: 2, color: color},
        {from: 1,   to: 3, value: 2, color: color},
        {from: 3,   to: 4, value: 2, color: color},
        {from: 3,   to: 5, value: 2, color: color},
        {from: 3,   to: 6, value: 2, color: color},
        {from: 3,   to: 7, value: 2, color: color},
        {from: 3,   to: 8, value: 2, color: color},
        {from: 3,   to: 9, value: 2, color: color}
      ];
      // create a network
      var container = document.getElementById('mynetwork');
      var data = {
        nodes: nodes,
        edges: edges
      };
      var options = {};
      network = new vis.Network(container, data, options);
    }
  </script>
  
</head>
<body onload="draw()">
	<center>
		<h1>VSLSystem</h1>
		<br>
		<br>
		<br>
		<br>
		
		
<div id="mynetwork"></div>

<div id="info"></div>
	</center>
</body>
</html>