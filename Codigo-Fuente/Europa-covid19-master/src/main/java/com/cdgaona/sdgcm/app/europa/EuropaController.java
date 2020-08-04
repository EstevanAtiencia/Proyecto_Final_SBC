package com.cdgaona.sdgcm.app.europa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.json.simple.JSONObject;

@RestController
public class EuropaController {

	@GetMapping("/")
	public String index() {
		return "Hola mundo.";
	}

	@GetMapping("/pruebas")
	public List<JSONObject> query() {
		List<JSONObject> list = new ArrayList<>();
		String urlDB = "jdbc:virtuoso://54.198.64.112:1111";
		VirtGraph set = new VirtGraph(urlDB, "dba", "mysecret");
		String queryString = "PREFIX dbo: <http://dbpedia.org/ontology/>"
				+ "			PREFIX schema: <http://schema.org/>"
				+ "			PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "			PREFIX gn: <http://www.geonames.org/ontology#>"
				+ "			prefix newOnto:<http://utpl.edu.ec/sbc/data/Ontology/>"
				+ "			prefix data:<http://utpl.edu.ec/sbc/dataCOVID/>"
				+ "			prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "			prefix dbr:<http://dbpedia.org/resource/>"
				+ "			PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + "" +

				"			select ?name ?TestByDay ?date from <http://54.198.64.112:8890/europav1> where { "
				+ "			?URI rdf:type newOnto:Pruebas ;" + "			     newOnto:quantity ?TestByDay;"
				+ "			     gn:locatedIn ?country;" + "			     schema:observationDate ?date."
				+ "			    ?country dbo:name ?name . " + "			    Filter regex(?name, \"France\") "
				+ "			}ORDER BY DESC (xsd:integer(?TestByDay))";

		Query query = QueryFactory.create(queryString);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(query, set);

		String message = "";
		JsonArray array = new JsonArray();
		int x = 0;
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				x++;
				JSONObject json = new JSONObject();
				QuerySolution soln = results.nextSolution();
				RDFNode nombre = soln.get("name");
				json.put("pais", soln.get("name").toString());
				RDFNode prueba = soln.get("TestByDay");
				json.put("pruebas", soln.get("TestByDay").toString());
				RDFNode fecha = soln.get("date");
				json.put("fecha", soln.get("date").toString());
				list.add(json);
				// message = array.toString();
			}
			System.out.println(x);
			return list;
		} finally {
			vqe.close();
		}
		// return null;
	}

	@GetMapping("/muertes")
	public List<JSONObject> muertes() {
		List<JSONObject> list = new ArrayList<>();
		String urlDB = "jdbc:virtuoso://54.198.64.112:1111";
		VirtGraph set = new VirtGraph(urlDB, "dba", "mysecret");
		String queryString = "PREFIX dbo: <http://dbpedia.org/ontology/>" + "PREFIX schema: <http://schema.org/>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX gn: <http://www.geonames.org/ontology#>"
				+ "prefix newOnto:<http://utpl.edu.ec/sbc/data/Ontology/>"
				+ "prefix data:<http://utpl.edu.ec/sbc/dataCOVID/>"
				+ "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "prefix dbr:<http://dbpedia.org/resource/>" + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "select ?nombre ?cantidad ?fecha from <http://54.198.64.112:8890/europav1> where { "
				+ "?var rdf:type newOnto:Muerte_Cases ;" + "     newOnto:totalQuantity ?cantidad;"
				+ "     gn:locatedIn ?pais;" + "     schema:observationDate ?fecha."
				+ "    ?pais dbo:name ?nombre .          " + "}ORDER BY DESC (xsd:integer(?cantidad))";

		Query query = QueryFactory.create(queryString);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(query, set);

		String message = "";
		JsonArray array = new JsonArray();
		int x = 0;
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				x++;
				JSONObject json = new JSONObject();
				QuerySolution soln = results.nextSolution();
				RDFNode nombre = soln.get("nombre");
				json.put("pais", soln.get("nombre").toString());
				RDFNode cantidad = soln.get("cantidad");
				json.put("Muertes", soln.get("cantidad").toString());
				RDFNode fecha = soln.get("fecha");
				json.put("fecha", soln.get("fecha").toString());
				/*
				 * RDFNode fecha = soln.get("date"); json.put("fecha",
				 * soln.get("date").toString()); RDFNode Test = soln.get("TestByDay");
				 * json.put("Prueba", soln.get("TestByDay").toString());
				 */
				list.add(json);
				// message = array.toString();
			}
			System.out.println(x);
			return list;
		} finally {
			vqe.close();
		}
		// return null;
	}

	@GetMapping("/casos")
	public List<JSONObject> casosAcumulativos() {
		List<JSONObject> list = new ArrayList<>();
		String urlDB = "jdbc:virtuoso://54.198.64.112:1111";
		VirtGraph set = new VirtGraph(urlDB, "dba", "mysecret");
		String queryString = "PREFIX dbo: <http://dbpedia.org/ontology/>" + "PREFIX schema: <http://schema.org/>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX gn: <http://www.geonames.org/ontology#>"
				+ "prefix newOnto:<http://utpl.edu.ec/sbc/data/Ontology/>"
				+ "prefix data:<http://utpl.edu.ec/sbc/dataCOVID/>"
				+ "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "prefix dbr:<http://dbpedia.org/resource/>" + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "select  ?name ?date ?CasesByDay from <http://54.198.64.112:8890/europav1> where { "
				+ "?URI rdf:type newOnto:Confirmed_Cases ;" + "     newOnto:totalQuantity ?CasesByDay;"
				+ "     gn:locatedIn ?country;" + "     schema:observationDate ?date."
				+ "    ?country dbo:name ?name .          " + "}ORDER BY DESC (xsd:integer(?CasesByDay))" + "";

		Query query = QueryFactory.create(queryString);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(query, set);

		String message = "";
		JsonArray array = new JsonArray();
		int x = 0;
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				x++;
				JSONObject json = new JSONObject();
				QuerySolution soln = results.nextSolution();
				RDFNode nombre = soln.get("name");
				json.put("pais", soln.get("name").toString());
				RDFNode cantidad = soln.get("CasesByDay");
				json.put("Casos", soln.get("CasesByDay").toString());
				RDFNode fecha = soln.get("date");
				json.put("fecha", soln.get("date").toString());
				/*
				 * RDFNode fecha = soln.get("date"); json.put("fecha",
				 * soln.get("date").toString()); RDFNode Test = soln.get("TestByDay");
				 * json.put("Prueba", soln.get("TestByDay").toString());
				 */
				list.add(json);
				// message = array.toString();
			}
			System.out.println(x);
			return list;
		} finally {
			vqe.close();
		}
		// return null;
	}



	@GetMapping("/ubicacion")
	public List<JSONObject> ubicacionPaises() {
		List<JSONObject> list = new ArrayList<>();
		String urlDB = "jdbc:virtuoso://54.198.64.112:1111";
		VirtGraph set = new VirtGraph(urlDB, "dba", "mysecret");
		String queryString = "PREFIX dbo: <http://dbpedia.org/ontology/>" + "PREFIX schema: <http://schema.org/>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX gn: <http://www.geonames.org/ontology#>"
				+ "prefix newOnto:<http://utpl.edu.ec/sbc/data/Ontology/>"
				+ "prefix data:<http://utpl.edu.ec/sbc/dataCOVID/>"
				+ "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "prefix dbr:<http://dbpedia.org/resource/>" + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "PREFIX : <http://dbpedia.org/ontology/>" + ""
				+ "select  ?name ?date ?cases ?latitude ?longitude from <http://54.198.64.112:8890/europav1> where { " + "?URI rdf:type newOnto:Confirmed_Cases ;"
				+ "     newOnto:totalQuantity ?cases;" + "     gn:locatedIn ?country;" + "     "
				+ "     schema:observationDate ?date." + "    ?country dbo:name ?name ;"
				+ "             schema:latitude ?latitude;" + "     schema:longitude ?longitude ."
				+ "    FILTER(?date >= \"15/06/2020\"^^xsd:string &&" + "         ?date <= \"15/06/2020\"^^xsd:string)"
				+ "}ORDER BY DESC (xsd:integer(?cases))" + "";

		Query query = QueryFactory.create(queryString);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(query, set);

		String message = "";
		JsonArray array = new JsonArray();
		int x = 0;
		try {
			ResultSet results = vqe.execSelect();
			while (results.hasNext()) {
				x++;
				JSONObject json = new JSONObject();
				QuerySolution soln = results.nextSolution();
				RDFNode nombre = soln.get("name");
				json.put("pais", soln.get("name").toString());
				RDFNode date = soln.get("date");
				json.put("fecha", soln.get("date").toString());
				RDFNode casos = soln.get("cases");
				json.put("casos", soln.get("cases").toString());
				RDFNode laitude = soln.get("latitude");
				json.put("latitud", soln.get("latitude").toString());
				RDFNode longitud = soln.get("longitude");
				json.put("longitud", soln.get("longitude").toString());
	
				list.add(json);
				// message = array.toString();
			}
			System.out.println(x);
			return list;
		} finally {
			vqe.close();
		}
		// return null;
	}
}
