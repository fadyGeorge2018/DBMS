package saving;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SaveXML {
	public void saveXML(LinkedList<LinkedList<LinkedList<String>>> tableData ,LinkedList<LinkedList<String>> saveColumnNames) {
		try {
			saveList(saveColumnNames);
			DtdWriter dtdWriter = new DtdWriter();
			for (int i=0 ; i<tableData.size() ; i++) {
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
				Document doc = docBuilder.newDocument();
				LinkedList<String> tableDatabaseNames = tableData.get(i).get(0);
				Element rootElement = doc.createElement(tableDatabaseNames.get(0));
				doc.appendChild(rootElement);
				Path path = Paths.get(""+tableDatabaseNames.get(0));
			    File h = Files.createDirectories(path).toAbsolutePath().normalize().toFile();
			    Element element=null;
			    if(tableDatabaseNames.size()!=1){ // if there is not table name
					 element = doc.createElement(""+tableDatabaseNames.get(1));
					rootElement.appendChild(element);
			    }
				ArrayList<String> columnNames = new ArrayList<>();
				for (int j=1 ; j<tableData.get(i).size() ; j++) {
					String columnName = tableData.get(i).get(j).get(1);
					columnNames.add(columnName);
					Element e = doc.createElement(columnName);
					element.appendChild(e);
					String dataType = tableData.get(i).get(j).get(0)+";";
					String data = dataType;
					for (int k=2 ; k<tableData.get(i).get(j).size() ; k++) {
						data += tableData.get(i).get(j).get(k)+";";
					}
					e.appendChild(doc.createTextNode(data));
				}
				  if(tableDatabaseNames.size()!=1){ // if there is not table name
						dtdWriter.MakeDtdFile(tableDatabaseNames.get(0), tableDatabaseNames.get(1), false, columnNames);
				    }
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
	            Transformer transformer = transformerFactory.newTransformer();
	            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	            if(tableDatabaseNames.size()!=1){
	            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, tableDatabaseNames.get(1)+ ".dtd");
				PrintWriter pw = new PrintWriter(h.getAbsolutePath()+"/"+tableDatabaseNames.get(1)+".xml");
	            DOMSource source = new DOMSource(doc);
	            StreamResult result = new StreamResult(pw);
	            transformer.transform(source, result);
	            pw.close();
	            }
			}
		} catch (ParserConfigurationException | TransformerException | IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public LinkedList<LinkedList<LinkedList<String>>> loadXML (LinkedList<LinkedList<String>> tableDatabaseNames) {
		LinkedList<LinkedList<LinkedList<String>>> returnTable = new LinkedList<>();
		try {
			for (int l=0 ; l<tableDatabaseNames.size() ; l++) {
				File xmlFile = new File(tableDatabaseNames.get(l).get(0)+"/"+tableDatabaseNames.get(l).get(1)+".xml");
				
		        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder dBuilder;
				dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(xmlFile);
				NodeList table = doc.getElementsByTagName(tableDatabaseNames.get(l).get(1));
				LinkedList<LinkedList<String>> tableContent = new LinkedList<>();
				LinkedList<String> dbNameAndTableName = new LinkedList<>();
				dbNameAndTableName.add(tableDatabaseNames.get(l).get(0));
				dbNameAndTableName.add(tableDatabaseNames.get(l).get(1));
				tableContent.add(dbNameAndTableName);
				for (int i=0 ; i<table.getLength() ; i++) {
					Node n = table.item(i);
					if (n.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) n;
						for (int j=2 ; j<tableDatabaseNames.get(l).size() ; j++) {
							LinkedList<String> list = new LinkedList<>();
							String line = eElement.getElementsByTagName(tableDatabaseNames.get(l).get(j)).item(0).getTextContent();
							String[] separatedLine = line.split(";");
							for (int k=0 ; k<separatedLine.length ; k++) {
								if (k == 1) {
									list.add(tableDatabaseNames.get(l).get(j));
								}
								list.add(separatedLine[k]);
							}
							tableContent.add(list);
						}
					}
				}
				returnTable.add(tableContent);
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {//exepction
		}
		return returnTable;
	}
	private void DeleteContaingFolder(String databaseName){
		File index = new File(databaseName);
		if(index.exists()){
			index.delete();
		}
	}
	
	public void dropDatabase(String databaseName) {
		File index = new File(databaseName);
		if (!index.exists()) {
			index.mkdir();
		} else {
			String[] entries = index.list();
			for (String s : entries) {
				File currentFile = new File(index.getPath(), s);
				currentFile.delete();
			}
		}
		DeleteContaingFolder(databaseName);
	}

	private void DeleteXmlFile(String databaseName, String tableName) {
		File xml = new File(databaseName + "/" + tableName + ".xml");
		if(xml.exists()){
		xml.delete();
		}
	}

	private void DeleteDtdFile(String databaseName, String tableName) {
		File dtd = new File(databaseName + "/" + tableName + ".dtd");
		if(dtd.exists()){
		dtd.delete();
		}
	}

	public void dropTable(String databaseName, String tableName) {
		DeleteXmlFile(databaseName, tableName);
		DeleteDtdFile(databaseName, tableName);
	}	
	
	public LinkedList<LinkedList<String>> getSavedList() {
		LinkedList<LinkedList<String>> list = new LinkedList<>();
		try {
			BufferedReader b = new BufferedReader(new FileReader("list.txt"));
			boolean isfirst = true;
			LinkedList<String> singleList =null;
			for(String line;( line=b.readLine() )!=null;) {
				if (line.contains("*") && !isfirst) {
					list.add(singleList);
					singleList = new LinkedList<>();
				} else if (line.contains("*") && isfirst) {
					singleList = new LinkedList<>();
					isfirst = false;
				} else {
					singleList.add(line);
				}
			}
			b.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
		return list;
	}
	
	
	private void saveList(LinkedList<LinkedList<String>> list) {
		File file = new File("list.txt");
		try {
			PrintWriter pw = new PrintWriter(file);
			for (int i=0 ; i<list.size() ; i++) {
				pw.println("*");
				for (int j=0 ; j<list.get(i).size() ; j++) {
					pw.println(list.get(i).get(j));
				}
			}
			pw.println("*");
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

}
