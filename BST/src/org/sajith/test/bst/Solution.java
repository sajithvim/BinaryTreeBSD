package org.sajith.test.bst;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Solution {

	public static void main(String[] args) throws IOException {
		System.out.println("---------- Instructions -----------");
		System.out.println("Follow the following input pattern.");
		System.out.println("\t First line -> Number of queries");
		System.out.println("\t Subsequent two line sequence -> ");
		System.out.println("\t \t Line 1 - Number of inputs");
		System.out.println("\t \t Line 2 - Input numbers separated by spaces");
		System.out.println("Output will be, if BST returns YES and if not NO");
		Solution solution = new Solution();
		solution.startScan();
	}

	public void startScan() throws IOException {
		Scanner in = new Scanner(System.in);
		int q = 0;
		q = Integer.parseInt(in.nextLine().trim());
		Query[] queries = new Query[q];
		for (int i = 0; i < queries.length; i++) {
			int num = Integer.parseInt(in.nextLine().trim());
			String numberStr = in.nextLine().trim();
			int[] numbers = new int[num];
			String[] splitNumbers = numberStr.split(" ");
			for (int j = 0; j < splitNumbers.length; j++) {
				numbers[j] = Integer.parseInt(splitNumbers[j]);
			}
			Query query = new Query(num, numbers);
			queries[i] = query;
		}

		String res = createTree(queries);
		System.out.println(res);
	}

	private String createTree(Query[] queries) {
		String res = null;
		Node rootNode = null;
		Node currentNode = null;
		for (int i = 0; i < queries.length; i++) {
			int[] numbers = queries[i].queryNumbers;
			for (int j = 0; j < numbers.length; j++) {
				if (rootNode == null) {
					rootNode = new Node();
					rootNode.value = numbers[j];
					currentNode = rootNode;
				} else {
					Node node = new Node();
					node.value = numbers[j];
					nodify(currentNode, node);
					currentNode = node;
				}
			}
			res = checkBSD(rootNode);
		}
		return res;
	}

	private String checkBSD(Node node) {
		String rhsRes = checkRHS(node.value, node.rhs);
		String lhsRes = checkLHS(node.value, node.rhs);
		return "YES".equals(rhsRes) && "YES".equals(lhsRes) ? "YES" : "NO";
	}

	private String checkLHS(int value, Node node) {
		if (node != null) {
			if (node.value > value) {
				return "NO";
			}
			if (node.rhs != null) {
				checkRHS(value, node.rhs);
			}
			if (node.lhs != null) {
				checkRHS(value, node.lhs);
			}
		}
		return "YES";

	}

	private String checkRHS(int value, Node node) {
		if (node != null) {
			if (node.value < value) {
				return "NO";
			}
			if (node.rhs != null) {
				checkRHS(value, node.rhs);
			}
			if (node.lhs != null) {
				checkRHS(value, node.lhs);
			}
		}
		return "YES";
	}

	private void nodify(Node rootNode, Node node) {
		if (node.value > rootNode.value) {
			if (rootNode.rhs == null) {
				rootNode.rhs = node;
			} else {
				nodify(rootNode.rhs, node);
			}
		} else if (node.value <= rootNode.value) {
			if (rootNode.lhs == null) {
				rootNode.lhs = node;
			} else {
				nodify(rootNode.lhs, node);
			}
		}

	}

	public class Node {
		int value;
		Node rhs;
		Node lhs;
	}

	public class Query {

		public Query(int num, int[] numbers) {
			numOfElements = num;
			queryNumbers = numbers;
		}

		int[] queryNumbers;
		int numOfElements;

	}

}