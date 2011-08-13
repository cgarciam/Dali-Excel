package org.apache.tika.parser.microsoft;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

import mx.home.util.Graph;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.apache.xmlbeans.XmlException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class ExcelExtractorTest {

	@Test
	public void test() throws IOException, SAXException, TikaException {
		final ParseContext parseContext = new ParseContext();
		final ExcelExtractor extractor = new ExcelExtractor(parseContext);
		extractor.setListenForAllRecords(true);
		final OutputStream out = new FileOutputStream(
				new File(getFileOutName()));
		final ContentHandler handler = new BodyContentHandler(out);
		final Metadata metadata = new Metadata();
		final XHTMLContentHandler xhtml = new XHTMLContentHandler(handler,
				metadata);
		final File inFile = new File(getFileInName());
		extractor.parse(new NPOIFSFileSystem(inFile), xhtml,
				new Locale("es_mx"));
	}

	private String getFileInName() {
		return DIR_PATHFILE_NAME + "Matriz de Adyacencia Dali Model.xls";
	}

	private String getFileOutName() {
		return DIR_PATHFILE_NAME + "Matriz de Adyacencia Dali Model out.txt";
	}

	private String getFileExtractorName() {
		return DIR_PATHFILE_NAME + "Libro5.xlsx";
	}

	public ExcelExtractorTest() {
		vertexName = new String[16];
		for (int i = 0; i < 16; i++) {
			vertexName[i] = "V" + i;
		}
	}

	@Test
	/**
	 * Esta prueba muestra como se puede pasar de un grafica especificada a 
	 * traves de una matriz de adyacencia (leida desde un archivo de Excel) a 
	 * una representacion que emplea listas de adyacencia.
	 * @throws XmlException
	 * @throws OpenXML4JException
	 * @throws IOException
	 */
	public void testFromMatrixToAdjacencyList() throws XmlException,
			OpenXML4JException, IOException {
		final XSSFExcelExtractor extractor = new XSSFExcelExtractor(
				getFileExtractorName());
		final String textFrowXlsBook = extractor.getText();
		LOG.debug("Extracted txt:\n" + textFrowXlsBook);
		final String[] textInArray = textFrowXlsBook.split("\t");
		LOG.debug("Number of tabs: " + textInArray.length);
		final StringBuilder inputPairs = new StringBuilder();
		for (String elemStr : textInArray) {
			if (elemStr.contains(",")) {
				if (elemStr.startsWith("Hoja1")) {
					/*
					 * La estructura puede ser 'Hoja1\n0,3\n1,2\n' en tal caso,
					 * no estaba leyendo al segundo par de datos (1,2)
					 */
					final String[] aux1 = elemStr.replace("Hoja1", "").split(
							"\n"); // \n0,3\n1,2\n
					inputPairs.append(aux1[1]);
					inputPairs.append(ELEM_SEPARATOR);
					inputPairs.append(aux1[2]);
				} else {
					inputPairs.append(elemStr.replace('\n', ' '));
				}
				inputPairs.append(ELEM_SEPARATOR);
			}
		}
		final String[] dataPairs = inputPairs.toString().split(ELEM_SEPARATOR);
		LOG.info("Number of Data Pairs: " + dataPairs.length);
		LOG.debug("Data Pairs: " + inputPairs.toString());
		// Fills the graph with read data pairs:
		final Graph graph = new Graph();
		for (final String edge : dataPairs) {
			LOG.trace(edge);
			final Integer vertexIni = Integer.parseInt(edge.split(",")[0]);
			final Integer vertexFin = Integer.parseInt(edge.split(",")[1]);
			graph.addEdge(vertexName[vertexIni], vertexName[vertexFin]);
		}
		LOG.debug("graph: " + graph);
	}

	private transient final String[] vertexName;

	private static final Logger LOG;
	private static final String DIR_PATHFILE_NAME;
	private static final String ELEM_SEPARATOR = " ";

	static {
		LOG = LoggerFactory.getLogger(ExcelExtractorTest.class);
		DIR_PATHFILE_NAME = "C:/Users/Cesar/Documents/development/BMV/";
	}

}
