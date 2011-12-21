package com.jspphp.tools.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * XML文件操作相关的工具类。该类的主要功能有：
 * <ul style="list-style-type:decimal">
 * <li>根据xml文件的名字获取Document对象。</li>
 * <li>根据字节输入流获取一个Document对象。</li>
 * <li>根据字符串获取一个Document对象。</li>
 * <li>通过XPath表达式获取单个节点。</li>
 * <li>通过XPath表达式获取多个节点。</li>
 * <li>通过XPath表达式获取字符串值。</li>
 * <li>通过XPath表达式获取布尔值。</li>
 * <li>将Document输出到指定的文件。</li>
 * <li>获取Node节点的属性值。</li>
 * <li>替换node节点。</li>
 * <li>将Node节点转换成字符串。</li>
 * </ul>
 * <br/>
 * <br/>
 * 
 * @author 史金波 创建日期：2009-09-09 Email:JSPPHP@126.COM
 * 
 */
public class XmlUtils {

	/**
	 * 获取Document对象。根据xml文件的名字获取Document对象。
	 * 
	 * @param file
	 *            要获取对象的xml文件全路径。
	 * @return 返回获取到的Document对象。
	 * @throws IOException
	 *             如果发生任何 IO 错误时抛出此异常。
	 * @throws SAXException
	 *             如果发生任何解析错误时抛出此异常。
	 * @throws ParserConfigurationException
	 *             如果无法创建满足所请求配置的 DocumentBuilder，将抛出该异常。
	 * @exception NullPointerException
	 *                如果file为空时，抛出此异常。
	 */
	public static Document parseForDoc(final String file) throws SAXException, IOException, SecurityException, NullPointerException, ParserConfigurationException {
		return XmlUtils.parseForDoc(new FileInputStream(file));
	}

	/**
	 * 将一个xml字符串解析成Document对象。
	 * 
	 * @param xmlStr
	 *            要被解析的xml字符串。
	 * @param encoding
	 *            字符串的编码。
	 * @return 返回解析后的Document对象。
	 * @throws IOException
	 *             如果发生任何 IO 错误时抛出此异常。
	 * @throws SAXException
	 *             如果发生任何解析错误时抛出此异常。
	 * @throws ParserConfigurationException
	 *             如果无法创建满足所请求配置的 DocumentBuilder，将抛出该异常。
	 */
	public static Document parseForDoc(String xmlStr, String encoding) throws SAXException, IOException, ParserConfigurationException {
		if (xmlStr == null)
			xmlStr = "";
		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(xmlStr.getBytes(encoding));
		return XmlUtils.parseForDoc(byteInputStream);
	}

	/**
	 * 获取Document对象。根据字节输入流获取一个Document对象。
	 * 
	 * @param is
	 *            获取对象的字节输入流。
	 * @return 返回获取到的Document对象。如果出现异常，返回null。
	 * @throws IOException
	 *             如果发生任何 IO 错误时抛出此异常。
	 * @throws SAXException
	 *             如果发生任何解析错误时抛出此异常。
	 * @throws ParserConfigurationException
	 *             如果无法创建满足所请求配置的 DocumentBuilder，将抛出该异常。
	 * @exception IllegalArgumentException
	 *                当 is 为 null 时抛出此异常。
	 */
	public static Document parseForDoc(final InputStream is) throws SAXException, IOException, ParserConfigurationException, IllegalArgumentException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(is);
		} finally {
			is.close();
		}
	}

	/**
	 * 通过xpath表达式解析某个xml节点。
	 * 
	 * @param obj
	 *            要被解析的xml节点对象。
	 * @param xPath
	 *            xpath表达式。
	 * @param qName
	 *            被解析的目标类型。
	 * @return 返回解析后的对象。
	 * @throws XPathExpressionException
	 *             如果不能计算 expression。
	 * 
	 * @exception RuntimeException
	 *                创建默认对象模型的 XPathFactory 遇到故障时。
	 * @exception NullPointerException
	 *                如果xPath为空时抛出时异常。
	 */
	private static Object parseByXpath(final Object obj, final String xPath, QName qName) throws NullPointerException, RuntimeException, XPathExpressionException {
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath path = xpathFactory.newXPath();
		return path.evaluate(xPath, obj, qName);
	}

	/**
	 * 通过XPath表达式获取单个节点。
	 * 
	 * @param obj
	 *            要被解析的對象。
	 * @param xPath
	 *            XPath表达式。
	 * @return 返回获取到的节点。
	 * 
	 * @throws XPathExpressionException
	 *             如果不能计算 expression。
	 * 
	 * @exception RuntimeException
	 *                创建默认对象模型的 XPathFactory 遇到故障时。
	 * @exception NullPointerException
	 *                如果xPath为空时抛出时异常。
	 */
	public static Node parseForNode(final Object obj, final String xPath) throws NullPointerException, RuntimeException, XPathExpressionException {
		return (Node) XmlUtils.parseByXpath(obj, xPath, XPathConstants.NODE);
	}

	/**
	 * 通过XPath表达式获取某个xml节点的字符串值。
	 * 
	 * @param obj
	 *            要被解析的對象。
	 * @param xPath
	 *            XPath表达式。
	 * @return 返回获取到的节点的字符串值。
	 * 
	 * @throws XPathExpressionException
	 *             如果不能计算 expression。
	 * 
	 * @exception RuntimeException
	 *                创建默认对象模型的 XPathFactory 遇到故障时。
	 * @exception NullPointerException
	 *                如果xPath为空时抛出时异常。
	 */
	public static String parseForString(final Object obj, final String xPath) throws NullPointerException, RuntimeException, XPathExpressionException {
		return (String) XmlUtils.parseByXpath(obj, xPath, XPathConstants.STRING);
	}

	/**
	 * 通过XPath表达式获取某个xml节点的布尔值。
	 * 
	 * @param obj
	 *            要被解析的對象。
	 * @param xPath
	 *            XPath表达式。
	 * @return 返回获取到的节点的布尔值。
	 * 
	 * @throws XPathExpressionException
	 *             如果不能计算 expression。
	 * 
	 * @exception RuntimeException
	 *                创建默认对象模型的 XPathFactory 遇到故障时。
	 * @exception NullPointerException
	 *                如果xPath为空时抛出时异常。
	 */
	public static Boolean parseForBoolean(final Object obj, final String xPath) throws NullPointerException, RuntimeException, XPathExpressionException {
		return (Boolean) XmlUtils.parseByXpath(obj, xPath, XPathConstants.BOOLEAN);
	}

	/**
	 * 通过XPath表达式获取Node列表。
	 * 
	 * @param obj
	 *            要被解析的對象。
	 * @param xPath
	 *            XPath表达式。
	 * @return 返回获取到的Node列表。
	 * 
	 * @throws XPathExpressionException
	 *             如果不能计算 expression。
	 * 
	 * @exception RuntimeException
	 *                创建默认对象模型的 XPathFactory 遇到故障时。
	 * @exception NullPointerException
	 *                如果xPath为空时抛出时异常。
	 */
	public static List<Node> parseForNodeList(final Object obj, final String xPath) throws NullPointerException, RuntimeException, XPathExpressionException {
		List<Node> lists = new ArrayList<Node>();
		NodeList nList = (NodeList) XmlUtils.parseByXpath(obj, xPath, XPathConstants.NODESET);
		if (nList != null)
			for (int i = 0; i < nList.getLength(); i++)
				lists.add(nList.item(i));
		return lists;
	}

	/**
	 * 获取节点的制定属性。
	 * 
	 * @param node
	 *            节点。
	 * @param attrName
	 *            属性名。
	 * @return 返回获取到的属性值。如果找不到相关的
	 * 
	 */
	public static String getAttribute(final Object node, final String attrName) {
		String result = "";
		if ((node != null) && (node instanceof Node)) {
			if (((Node) node).getNodeType() == Node.ELEMENT_NODE)
				result = ((Element) node).getAttribute(attrName);
			else {
				// 遍历整个xml某节点指定的属性
				NamedNodeMap attrs = ((Node) node).getAttributes();
				if ((attrs.getLength() > 0) && (attrs != null)) {
					Node attr = attrs.getNamedItem(attrName);
					result = attr.getNodeValue();
				}
			}
		}
		return result;
	}

	/**
	 * 使用新节点替换原来的旧节点。
	 * 
	 * @param oldNode
	 *            要被替换的旧节点。
	 * @param newNode
	 * 
	 *            替换后的新节点。
	 * @exception DOMException
	 *                如果此节点为不允许 newNode节点类型的子节点的类型；或者如果要放入的节点为此节点的一个祖先或此节点本身；或者如果此节点为 Document 类型且替换操作的结果将第二个 DocumentType 或 Element 添加到 Document 上。
	 *                WRONG_DOCUMENT_ERR: 如果 newChild 是从不同的文档创建的，不是从创建此节点的文档创建的，则引发此异常。 NO_MODIFICATION_ALLOWED_ERR: 如果此节点或新节点的父节点为只读的，则引发此异常。 NOT_FOUND_ERR: 如果
	 *                oldChild 不是此节点的子节点，则引发此异常。 NOT_SUPPORTED_ERR: 如果此节点为 Document 类型，则如果 DOM 实现不支持替换 DocumentType 子节点或 Element 子节点，则可能引发此异常。
	 */
	public static void replaceNode(Node oldNode, Node newNode) {
		if ((oldNode != null) && (newNode != null))
			oldNode.getParentNode().replaceChild(newNode, oldNode);
	}

	/**
	 * 将Document输出到指定的文件中。
	 * 
	 * @param fileName
	 *            文件名。
	 * @param node
	 *            要保存的对象。
	 * @param encoding
	 *            保存的编码。
	 * @throws FileNotFoundException
	 *             指定的文件名不存在时，抛出此异常。
	 * @throws TransformerException
	 *             如果转换过程中发生不可恢复的错误时，抛出此异常。
	 */
	public static void saveXml(final String fileName, final Node node, String encoding) throws FileNotFoundException, TransformerException {
		XmlUtils.writeXml(new FileOutputStream(fileName), node, encoding);
	}

	/**
	 * 将Document输出成字符串的形式。
	 * 
	 * @param node
	 *            Node对象。
	 * @param encoding
	 *            字符串的编码。
	 * @return 返回输出成的字符串。
	 * @throws TransformerException
	 *             如果转换过程中发生不可恢复的错误时，抛出此异常。
	 * @throws UnsupportedEncodingException
	 *             指定的字符串编码不支持时，抛出此异常。
	 */
	public static String nodeToString(Node node, String encoding) throws TransformerException, UnsupportedEncodingException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		XmlUtils.writeXml(outputStream, node, encoding);
		return outputStream.toString(encoding);
	}

	/**
	 * 将指定的Node写到指定的OutputStream流中。
	 * 
	 * @param encoding
	 *            编码。
	 * @param os
	 *            OutputStream流。
	 * @param node
	 *            Node节点。
	 * @throws TransformerException
	 *             如果转换过程中发生不可恢复的错误时，抛出此异常。
	 */
	private static void writeXml(OutputStream os, Node node, String encoding) throws TransformerException {
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transformer = transFactory.newTransformer();
		transformer.setOutputProperty("indent", "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
		DOMSource source = new DOMSource();
		source.setNode(node);
		StreamResult result = new StreamResult();
		result.setOutputStream(os);
		transformer.transform(source, result);
	}

	public static Element getElement(Document doc, String tagName, int index) {
		NodeList rows = doc.getDocumentElement().getElementsByTagName(tagName);
		return (Element) rows.item(index);
	}

	public static int getSize(Document doc, String tagName) {
		NodeList rows = doc.getDocumentElement().getElementsByTagName(tagName);
		return rows.getLength();
	}

	public static String doc2String(Document doc, String encoding) {
		StringWriter stringOut;
		doc.normalize();
		OutputFormat format = new OutputFormat(doc);
		format.setEncoding(encoding);
		format.setVersion("1.0");
		format.setIndenting(true);
		stringOut = new StringWriter();
		XMLSerializer serial = new XMLSerializer(stringOut, format);
		try {
			serial.asDOMSerializer();
			serial.serialize(doc.getDocumentElement());
			return stringOut.toString();
		} catch (IOException e) {
			return null;
		}
	}

	public static Document parseXMLFile(String strFile) {
		Document doc;
		FileInputStream is;
		try {
			// File f=new File(strFile);
			is = new FileInputStream(strFile);
			DocumentBuilderFactory dd = DocumentBuilderFactory.newInstance();
			doc = dd.newDocumentBuilder().parse(is);
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return Document
	 */
	public static Document newDocument() {
		Document doc = null;
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = db.newDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * 创建指定名称的根元素
	 * 
	 * @param ElementName
	 *            元素名称
	 * @return Element 根元素节点
	 */
	public static Element createRootElement(String ElementName) {
		Element rootElement = null;
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = db.newDocument();
			rootElement = doc.createElement(ElementName);
		} catch (Exception e) {
		}
		return rootElement;
	}

	/**
	 * 通过XML文档的路径获得XML文档根节点
	 * 
	 * @param fileName
	 *            XML文档的路径,如"lib/book.xml"
	 * @return Element 根节点
	 */
	public static Element getRootElement(String fileName) {
		if (fileName == null || fileName.length() == 0)
			return null;
		try {
			Element rootElement = null;
			synchronized (XmlUtils.class) {
				FileInputStream fis = new FileInputStream(fileName);
				rootElement = getRootElement(fis);
				fis.close();
			}
			return rootElement;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 通过字节输入流获得XML文档根节点
	 * 
	 * @param is
	 *            字节输入流
	 * @return Element 根节点
	 */
	public static Element getRootElement(InputStream is) {
		Document doc = null;
		if (is == null)
			return null;
		Element rootElement = null;
		try {
			synchronized (XmlUtils.class) {
				DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				doc = db.parse(is);
				rootElement = doc.getDocumentElement();
			}
		} catch (Exception e) {
		}
		return rootElement;
	}

	/**
	 * 通过输入源获得XML文档根节点
	 * 
	 * @param is
	 *            输入源
	 * @return Element 根节点
	 */
	public static Element getRootElement(InputSource is) {
		if (is == null)
			return null;
		Element rootElement = null;
		try {
			synchronized (XmlUtils.class) {
				DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc = db.parse(is);
				rootElement = doc.getDocumentElement();
			}
		} catch (Exception e) {
		}
		return rootElement;
	}

	/**
	 * 获得所有Element类型的子节点
	 * 
	 * @param element
	 *            父节点
	 * @return Element[] 所有Element类型的子节点
	 */
	public static Element[] getChildElements(Element element) {
		if (element == null)
			return null;
		List<Element> childs = new ArrayList<Element>();
		synchronized (XmlUtils.class) {
			for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling())
				if (node instanceof Element)
					childs.add((Element) node);
		}
		Element[] elmt = new Element[childs.size()];
		childs.toArray(elmt);
		return elmt;
	}

	/**
	 * 获得指定名称的所有Element类型的子节点
	 * 
	 * @param element
	 *            父节点
	 * @param childName
	 *            节点名称
	 * @return Element[] 所有Element类型的子节点
	 */
	public static Element[] getChildElements(Element element, String childName) {
		if (element == null || childName == null || childName.length() == 0)
			return null;
		List<Element> childs = new ArrayList<Element>();
		synchronized (XmlUtils.class) {
			for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling())
				if (node instanceof Element)
					if (node.getNodeName().equals(childName))
						childs.add((Element) node);
		}
		Element[] elmt = new Element[childs.size()];
		childs.toArray(elmt);
		return elmt;
	}

	/**
	 * 获得所有子节点，包括Element类型和Text类型，一个元素的直接子节点可以是元素类型也可以是Text类型
	 * 
	 * @param node
	 *            父节点
	 * @return Node[] 所有子节点
	 */
	public static Node[] getChildNodes(Node node) {
		if (node == null)
			return null;
		List<Node> childs = new ArrayList<Node>();
		synchronized (XmlUtils.class) {
			for (Node n = node.getFirstChild(); n != null; n = n.getNextSibling())
				childs.add(n);
		}
		Node[] childNodes = new Node[childs.size()];
		childs.toArray(childNodes);
		return childNodes;
	}

	/**
	 * 获得第一个以childName命名的Element类型的子节点
	 * 
	 * @param element
	 *            父节点
	 * @param childName
	 *            节点名称
	 * @return Element 第一个Element类型的子节点
	 */
	public static Element getChildElement(Element element, String childName) {
		if (element == null || childName == null || childName.length() == 0)
			return null;
		Element childs = null;
		synchronized (XmlUtils.class) {
			for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
				if (node instanceof Element) {
					if (node.getNodeName().equals(childName)) {
						childs = (Element) node;
						break;
					}
				}
			}
		}
		return childs;
	}

	/**
	 * 获得第一个Element类型的子节点
	 * 
	 * @param element
	 *            父节点
	 * @return Element Element类型的子节点
	 */
	public static Element getChildElement(Element element) {
		if (element == null)
			return null;
		Element childs = null;
		synchronized (XmlUtils.class) {
			for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
				if (node instanceof Element) {
					childs = (Element) node;
					break;
				}
			}
		}
		return childs;
	}

	/**
	 * 获得直接子节点的文本内容，包括特殊字符串，如null或"\n\t"
	 * 
	 * @param element
	 *            父节点
	 * @return String[] 所有子节点文本内容
	 */
	public static String[] getElementValues(Element element) {
		if (element == null)
			return null;
		List<String> childs = new ArrayList<String>();
		for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling())
			if (node instanceof Text)
				childs.add(node.getNodeValue());
		String[] values = new String[childs.size()];
		childs.toArray(values);
		return values;
	}

	/**
	 * 获得直接子节点的纯文本内容，不包括特殊字符串，如null或"\n\t"
	 * 
	 * @param element
	 *            父节点
	 * @return String
	 */
	public static String getElementValue(Element element) {
		if (element == null)
			return null;
		String retnStr = null;
		for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node instanceof Text) {
				String str = node.getNodeValue();
				if (str == null || str.length() == 0 || str.trim().length() == 0)
					continue;
				else {
					retnStr = str;
					break;
				}
			}
		}
		return retnStr;
	}

	/**
	 * 以文档顺序检索具有给定名称的第一个元素
	 * 
	 * @param e
	 *            父节点
	 * @param name
	 *            节点名称
	 * @return Element 节点信息
	 */
	public static Element findElementByName(Element e, String name) {
		if (e == null || name == null || name.length() == 0)
			return null;
		String nodename = null;
		synchronized (XmlUtils.class) {
			Element[] childs = getChildElements(e);
			for (int i = 0; i < childs.length; i++) {
				nodename = childs[i].getNodeName();
				if (name.equals(nodename))
					return childs[i];
			}
			// 若直接子节点中不存在则在孙子节点中查找
			for (int i = 0; i < childs.length; i++) {
				Element retn = findElementByName(childs[i], name);
				if (retn != null)
					return retn;
			}
		}
		return null;
	}

	/**
	 * 通过属性名称和属性值获得元素节点
	 * 
	 * @param e
	 *            父节点
	 * @param attrName
	 *            属性名称
	 * @param attrVal
	 *            属性值
	 * @return Element 节点信息
	 */
	public static Element findElementByAttr(Element e, String attrName, String attrVal) {
		return findElementByAttr(e, attrName, attrVal, true);
	}

	/**
	 * 获得子节点中满足属性名称和属性值的元素节点
	 * 
	 * @param e
	 *            父节点
	 * @param attrName
	 *            属性名称
	 * @param attrVal
	 *            属性值
	 * @param dept
	 *            是否在所有后代节点中查找
	 * @return Element 元素节点
	 */
	public static Element findElementByAttr(Element e, String attrName, String attrVal, boolean dept) {
		if (e == null || attrName == null || attrName.length() == 0 || attrVal == null || attrVal.length() == 0)
			return null;
		String tmpValue = null;
		synchronized (XmlUtils.class) {
			Element[] childs = getChildElements(e);
			// 防止对共享数据的访问冲突
			for (int i = 0; i < childs.length; i++) {
				tmpValue = childs[i].getAttribute(attrName);
				if (attrVal.equals(tmpValue))
					return childs[i];
			}
			// 若子节点中不存在与属性名称和属性值匹配的元素，则在后代节点中继续查找
			if (dept) {
				for (int i = 0; i < childs.length; i++) {
					Element retn = findElementByAttr(childs[i], attrName, attrVal);
					if (retn != null)
						return retn;
				}
			}
		}
		return null;
	}

	/**
	 * 以字符串格式输出XML文档内容，参数可以为XML文档中的任意一个元素
	 * 
	 * @param e
	 *            XML文档中的任意一个元素
	 * @return String XML文档的字符串表示
	 */
	public static String formatXml(Element e) {
		StringWriter writer = new StringWriter();
		StreamResult sResult = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		String result = null;

		try {
			Transformer t = tf.newTransformer();
			synchronized (XmlUtils.class) {
				Document doc = e.getOwnerDocument();
				DOMSource doms = new DOMSource(doc);
				t.transform(doms, sResult);
				result = writer.toString();
			}
		} catch (Exception e1) {
		}

		return result;
	}

	/**
	 * 添加一个新属性
	 * 
	 * @param e
	 *            父节点
	 * @param name
	 *            属性名称
	 * @param value
	 *            属性值
	 */
	public static void setAttribute(Element e, String name, String value) {
		if (e == null || name == null || name.length() == 0 || value == null || value.length() == 0)
			return;
		else {
			// 当两个并发线程访问该方法中的synchronized(XmlUtils.class)同步代码块时，同一时刻只有获得锁的线程得以执行，
			// 另一个线程必须等待当前线程执行完这个代码块才能执行，从而有效避免了对共享数据的访问冲突
			synchronized (XmlUtils.class) {
				e.setAttribute(name, value);
			}
		}
	}

	/**
	 * 通过属性名称获得指定元素的属性值，若不存在该属性则返回null
	 * 
	 * @param e
	 *            元素节点
	 * @param name
	 *            属性名称
	 * @return String 属性值，若不存在该属性则返回null
	 */
	public static String getAttribute(Element e, String name) {
		return getAttribute(e, name, null);
	}

	/**
	 * 通过属性名称获得指定元素的属性值，若不存在该属性则返回默认字符串
	 * 
	 * @param e
	 *            元素节点
	 * @param name
	 *            属性名称
	 * @param defval
	 *            默认字符串
	 * @return String 属性值，若不存在该属性则返回默认值
	 */
	public static String getAttribute(Element e, String name, String defval) {
		if (e == null || name == null || name.length() == 0)
			return defval;
		else {
			// 当线程1访问该类的setAttribute方法中的synchronized
			// (XmlUtils.class)块或其它synchronized (XmlUtils.class)块时，
			// 则其它线程对以下同步代码块的访问将被阻塞，需等待线程1释放锁，获得锁后方能执行该代码块，这样有效的解决了对共享数据的访问冲突。
			synchronized (XmlUtils.class) {
				return e.getAttribute(name);
			}
		}
	}

	/**
	 * 转换XML文档
	 * 
	 * @param doc
	 *            XML文档中欲保留的元素节点及所有子节点
	 * @param filename
	 *            XML文档路径
	 * @throws Exception
	 */
	public static void transformerWrite(Element doc, String filename) throws Exception {
		// 创建带有DOM节点的输入源
		DOMSource doms = new DOMSource(doc);
		File f = new File(filename);
		StreamResult sr = new StreamResult(f);
		transformerWrite(doms, sr);
	}

	/**
	 * 转换XML文档
	 * 
	 * @param doc
	 *            XML文档中欲保留的元素节点及所有子节点
	 * @param file
	 *            File 实例
	 * @throws Exception
	 */
	public static void transformerWrite(Element doc, File file) throws Exception {
		// 创建带有DOM节点的输入源
		DOMSource doms = new DOMSource(doc);
		StreamResult sr = new StreamResult(file);
		transformerWrite(doms, sr);
	}

	/**
	 * 转换XML文档
	 * 
	 * @param doc
	 *            XML文档中欲保留的元素节点及所有子节点
	 * @param outstream
	 *            输出字节流
	 * @throws Exception
	 */
	public static void transformerWrite(Element doc, OutputStream outstream) throws Exception {
		// 创建带有DOM节点的输入源
		DOMSource doms = new DOMSource(doc);
		StreamResult sr = new StreamResult(outstream);
		transformerWrite(doms, sr);
	}

	/**
	 * 转换XML文档
	 * 
	 * @param doc
	 *            XML文档中欲保留的元素节点及所有子节点
	 * @param outwriter
	 *            写入字符流
	 * @throws Exception
	 */
	public static void transformerWrite(Element doc, Writer outwriter) throws Exception {
		// 创建带有DOM节点的输入源
		DOMSource doms = new DOMSource(doc);
		StreamResult sr = new StreamResult(outwriter);
		transformerWrite(doms, sr);
	}

	/**
	 * 转换XML文档，编码采用UTF-8
	 * 
	 * @param doms
	 *            DOMSource实例
	 * @param sr
	 *            转换结果的持有者
	 * @throws Exception
	 */
	public static void transformerWrite(DOMSource doms, StreamResult sr) throws Exception {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		t.transform(doms, sr);
	}

	/**
	 * 转换XML文档，编码格式可根据参数指定
	 * 
	 * @param doms
	 *            DOMSource实例
	 * @param sr
	 *            转换结果的持有者
	 * @param encode
	 *            编码格式
	 * @throws Exception
	 */
	public static void transformerWrite(DOMSource doms, StreamResult sr, String encode) throws Exception {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		t.setOutputProperty(OutputKeys.ENCODING, encode);
		t.transform(doms, sr);
	}
}
