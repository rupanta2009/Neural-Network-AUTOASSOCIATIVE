//Program			: PROJECT-1 (AUTO ASSOCIATIVE NEURAL NET)
//Name				: RUPANTA RWITEEJ DUTTA
//Email Address		: rrd300@nyu.edu
//Date of Creation	: 12.04.2016
//School			: NYU Tandon School of Engineering
//NYU ID			: N15786532
//Net ID			: rrd300
//Subject			: Neural Network Computing

import java.io.*;
import java.util.*;

import Jama.Matrix;

public class autoAssociative {
	private static Scanner scan;
	private static Scanner scan1;
	public static double [][] input = new double[10][35];
	public static double [][] input1 = new double[10][35];
	public static double [][] bias = new double [1][35];
	public static double [][] bias1 = new double [1][35];
	public static double [][] bias2 = new double [1][35];
	public static double [][] bias11 = new double [1][35];
	public static double [][] bias12 = new double [1][35];
	public static double [][] bias13 = new double [1][35];
	public static double [][] bias14 = new double [1][35];
	public static double [][] weight = new double[35][35];
	private static double [][] output = new double[10][35];
	private static double [][] output1 = new double[5][35];
	private static double [][] output11 = new double[5][35];
	private static double [][] output12 = new double[5][35];
	private static double [][] output13 = new double[5][35];
	
	//Function to open input file
	public static void openFile()
		{
			try{
				System.out.println("=============================");
				System.out.println("TRYING TO OPEN INPUT FILE....");
				scan = new Scanner(new File("TenDigitPatterns.txt"));
			}
			catch(Exception e)
			{
				System.out.println("Unable to open Input File!!");
			}
		}
	
	//Function to read input file and store in a 2D array
	public static void readFile()
		{
			System.out.println("TRYING TO READ INPUT FILE....");
			int r = 0;
			while((scan.hasNextLine()))
			{
				int in;
				int count = 0;
				String line = scan.nextLine();
				for(int i = 0; i < line.length(); i++)
				{
					char ch = line.charAt(i);
					if (ch == '#')
					{
						in = 1;
					}
					else
					{
						in = -1;
					}
					input[r][i] = in;
					input1[r][i] = in;
					count++;
				}
				if(count < 35)
				{
					while(count < 35)
					{
						input[r][count] = -1;
						input1[r][count] = -1;
						count++;
					}
				}
			r++;
			}
		}
	
	//Function to close input file
	public static void closeFile()
		{
			System.out.println("TRYING TO CLOSE INPUT FILE...");
			System.out.println("=============================");
			System.out.println();
			scan.close();
		}
		
	//Main Function
	public static void main(String[] args)
	{
		openFile();
		readFile();
		closeFile();
		
		Matrix X = new Matrix(input);
		int i, j, k;
		
		//Clear contents of results file
		PrintWriter writer;
		PrintWriter writer1;
		try {
			writer = new PrintWriter("results.txt");
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e1) {
			System.out.println("Unable to clear results.txt file!!!");
		}
		try {
			writer1 = new PrintWriter("results1.txt");
			writer1.print("");
			writer1.close();
		} catch (FileNotFoundException e1) {
			System.out.println("Unable to clear results1.txt file!!!");
		}
		
		System.out.println("=============================");
		System.out.println("INPUT MATRIX CONTENTS");
		System.out.println("=============================");
		
		for(i=0; i<10; i++)
		{
			for(j=0; j<35; j++)
			{
				System.out.printf("%2d", (int)input[i][j]);
				System.out.print(' ');
			}
			System.out.println();
		}
		System.out.println("=============================");
		System.out.println();
		
		//Calculate weight by using 1 input vector at a time
		System.out.println("=============================");
		System.out.println("FINDING WEIGHTS USING 1 INPUT VECTOR...");
		System.out.println("=============================");
		double a[][] = new double[35][1];
		double b[][] = new double[35][1];
		double c[][] = new double[35][1];
		double d[][] = new double[35][1];
		double f[][] = new double[35][1];
		double g[][] = new double[35][1];
		double h[][] = new double[35][1];
		double h1[][] = new double[35][1];
		double h2[][] = new double[35][1];
		for(i=0; i<10; i++)
		{
			int count = 0;
			for(j=0; j<35; j++)
			{
				 a[j][0] = input[i][j];
			}
			Matrix A = new Matrix(a);
			Matrix W = new Matrix(weight);
			Matrix Yin = new Matrix(output);
			Matrix Bias = new Matrix(bias);
			for(int bi=0; bi<35; bi++)
			{
				Bias.set(0, bi, A.get(bi, 0));
			}
			W = (A).times(A.transpose());
			Yin = (X.times(W));
			for(int bi=0; bi<10; bi++)
			{
				for(int bi2=0; bi2<35; bi2++)
				{
					Yin.set(bi, bi2, Yin.get(bi, bi2)+Bias.get(0, bi2));
				}
			}
			double[][] valsTransposed = Yin.getArray();
			double[][] vals = X.getArray();
			for(int in = 0; in < valsTransposed.length; in++) {
			    for(int jn = 0; jn < valsTransposed[in].length; jn++) {        
			        if(valsTransposed[in][jn] < 0)
			        	valsTransposed[in][jn] = -1;
			        else if (valsTransposed[in][jn] == 0) //change here
			        	valsTransposed[in][jn] = 0;
			        else
			        	valsTransposed[in][jn] = 1;
			    }
			}
			Matrix Y = new Matrix(valsTransposed);
			double[][]vals1 = Y.getArray();
			for(int in = 0; in < vals1.length; in++) {
			    for(int jn = 0; jn < vals1[in].length; jn++) {        
			        if(vals[in][jn] != vals1[in][jn])
			        {
			        ++count;
			        break;
			        }
			    }
			}
			int ans = 10 - count;
			try(FileWriter fw = new FileWriter("results.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw))
				{
					out.println(ans + ", 1, " + i);    
				} catch (IOException e) {
				    System.out.println("Unable to write results to file!!!");
				}
			count = 0; 
			for(int in=0; in<35; in++)
				W.set(in, in, 0);;
			Yin = (X.times(W));
			for(int bi=0; bi<10; bi++)
			{
				for(int bi2=0; bi2<35; bi2++)
				{
					Yin.set(bi, bi2, Yin.get(bi, bi2)+Bias.get(0, bi2));
				}
			}
			valsTransposed = Yin.getArray();
			vals = X.getArray();
			for(int in = 0; in < valsTransposed.length; in++) {
			    for(int jn = 0; jn < valsTransposed[in].length; jn++) {        
			        if(valsTransposed[in][jn] < 0)
			        	valsTransposed[in][jn] = -1;
			        else if (valsTransposed[in][jn] == 0) //change here
			        	valsTransposed[in][jn] = 0;
			        else
			        	valsTransposed[in][jn] = 1;
			    }
			}
			Y = new Matrix(valsTransposed);
			vals1 = Y.getArray();
			for(int in = 0; in < vals1.length; in++) {
			    for(int jn = 0; jn < vals1[in].length; jn++) {        
			        if(vals[in][jn] != vals1[in][jn])
			        {
			        ++count;
			        break;
			        }
			    }
			}
			int ans1 = 10 - count;
			try(FileWriter fw = new FileWriter("results1.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw))
				{
					out.println(ans1 + ", 1, " + i);    
				} catch (IOException e) {
				    System.out.println("Unable to write results to file!!!");
				}
		System.out.println("Number of Correct Classifications: " + ans + ", Vector(s) Used while Training: " + i);
		}
		System.out.println("=============================");
		System.out.println();
		
		//Calculate weight by using 2 input vectors at a time
		System.out.println("=============================");
		System.out.println("FINDING WEIGHTS USING 2 INPUT VECTORS...");
		System.out.println("=============================");
		for (i=0; i<9; i++)
		{
			for (j=i+1; j<10; j++)
			{
				int count = 0;
				for (k=0; k<35; k++)
				{
					a[k][0] = input[i][k];
					b[k][0] = input[j][k];
				}
				Matrix A = new Matrix(a);
				Matrix B = new Matrix(b);
				Matrix W = new Matrix(weight);
				Matrix Yin = new Matrix(output);
				Matrix Bias = new Matrix(bias);
				for(int bi=0; bi<35; bi++)
				{
					Bias.set(0, bi, A.get(bi, 0) + B.get(bi, 0));
				}
				W = (A).times(A.transpose());
				W = (W).plus((B).times(B.transpose()));
				Yin = (X.times(W));
				for(int bi=0; bi<10; bi++)
				{
					for(int bi2=0; bi2<35; bi2++)
					{
						Yin.set(bi, bi2, Yin.get(bi, bi2)+Bias.get(0, bi2));
					}
				}
				double[][] valsTransposed = Yin.getArray();
				double[][] vals = X.getArray();
				for(int in = 0; in < valsTransposed.length; in++) {
				    for(int jn = 0; jn < valsTransposed[in].length; jn++) {        
				        if(valsTransposed[in][jn] < 0)
				        	valsTransposed[in][jn] = -1;
				        else if (valsTransposed[in][jn] == 0) //change here
				        	valsTransposed[in][jn] = 0;
				        else
				        	valsTransposed[in][jn] = 1;
				    }
				}
				Matrix Y = new Matrix(valsTransposed);
				double[][]vals1 = Y.getArray();
				for(int in = 0; in < vals1.length; in++) {
				    for(int jn = 0; jn < vals1[in].length; jn++) {        
				        if(vals[in][jn] != vals1[in][jn])
				        {
				        ++count;
				        break;
				        }
				    }
				}
				int ans = 10 - count;
				try(FileWriter fw = new FileWriter("results.txt", true);
						BufferedWriter bw = new BufferedWriter(fw);
						PrintWriter out = new PrintWriter(bw))
							{
								out.println(ans + ", 2, " + i + " " + j);    
							} catch (IOException e) {
							    System.out.println("Unable to write results to file!!!");
							}
				count = 0; 
				for(int in=0; in<35; in++)
					W.set(in, in, 0);;
				Yin = (X.times(W));
				for(int bi=0; bi<10; bi++)
				{
					for(int bi2=0; bi2<35; bi2++)
					{
						Yin.set(bi, bi2, Yin.get(bi, bi2)+Bias.get(0, bi2));
					}
				}
				valsTransposed = Yin.getArray();
				vals = X.getArray();
				for(int in = 0; in < valsTransposed.length; in++) {
				    for(int jn = 0; jn < valsTransposed[in].length; jn++) {        
				        if(valsTransposed[in][jn] < 0)
				        	valsTransposed[in][jn] = -1;
				        else if (valsTransposed[in][jn] == 0) //change here
				        	valsTransposed[in][jn] = 0;
				        else
				        	valsTransposed[in][jn] = 1;
				    }
				}
				Y = new Matrix(valsTransposed);
				vals1 = Y.getArray();
				for(int in = 0; in < vals1.length; in++) {
				    for(int jn = 0; jn < vals1[in].length; jn++) {        
				        if(vals[in][jn] != vals1[in][jn])
				        {
				        ++count;
				        break;
				        }
				    }
				}
				int ans1 = 10 - count;
				try(FileWriter fw = new FileWriter("results1.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw))
					{
						out.println(ans1 + ", 2, " + i + " " + j);    
					} catch (IOException e) {
					    System.out.println("Unable to write results to file!!!");
					}
					System.out.println("Number of Correct Classifications: " + ans + ", Vector(s) Used while Training: " + i + ", " + j);
			}
		}
		System.out.println("=============================");
		System.out.println();
		
		//Calculate weight by using 3 input vectors at a time
		System.out.println("=============================");
		System.out.println("FINDING WEIGHTS USING 3 INPUT VECTORS...");
		System.out.println("=============================");
		for (i=0; i<8; i++)
		{
			for (j=i+1; j<9; j++)
			{
				for (k=j+1; k<10; k++)
				{
					int count = 0;
					for (int l=0; l<35; l++)
					{
						a[l][0] = input[i][l];
						b[l][0] = input[j][l];
						c[l][0] = input[k][l];
					}
					Matrix A = new Matrix(a);
					Matrix B = new Matrix(b);
					Matrix C = new Matrix(c);
					Matrix W = new Matrix(weight);
					Matrix Yin = new Matrix(output);
					Matrix Bias = new Matrix(bias);
					for(int bi=0; bi<35; bi++)
					{
						Bias.set(0, bi, A.get(bi, 0) + B.get(bi, 0) + C.get(bi, 0));
					}
					W = (A).times(A.transpose());
					W = (W).plus((B).times(B.transpose()));
					W = (W).plus((C).times(C.transpose()));
					Yin = (X.times(W));
					for(int bi=0; bi<10; bi++)
					{
						for(int bi2=0; bi2<35; bi2++)
						{
							Yin.set(bi, bi2, Yin.get(bi, bi2)+Bias.get(0, bi2));
						}
					}
					double[][] valsTransposed = Yin.getArray();
					double[][] vals = X.getArray();
					for(int in = 0; in < valsTransposed.length; in++) {
					    for(int jn = 0; jn < valsTransposed[in].length; jn++) {        
					        if(valsTransposed[in][jn] < 0)
					        	valsTransposed[in][jn] = -1;
					        else if (valsTransposed[in][jn] == 0) //change here
					        	valsTransposed[in][jn] = 0;
					        else
					        	valsTransposed[in][jn] = 1;
					    }
					}
					Matrix Y = new Matrix(valsTransposed);
					double[][]vals1 = Y.getArray();
					for(int in = 0; in < vals1.length; in++) {
					    for(int jn = 0; jn < vals1[in].length; jn++) {        
					        if(vals[in][jn] != vals1[in][jn])
					        {
					        ++count;
					        break;
					        }
					    }
					}
					int ans = 10 - count;
					try(FileWriter fw = new FileWriter("results.txt", true);
							BufferedWriter bw = new BufferedWriter(fw);
							PrintWriter out = new PrintWriter(bw))
								{
									out.println(ans + ", 3, " + i + " " + j + " " + k);    
								} catch (IOException e) {
								    System.out.println("Unable to write results to file!!!");
								}
					count = 0; 
					for(int in=0; in<35; in++)
						W.set(in, in, 0);;
					Yin = (X.times(W));
					for(int bi=0; bi<10; bi++)
					{
						for(int bi2=0; bi2<35; bi2++)
						{
							Yin.set(bi, bi2, Yin.get(bi, bi2)+Bias.get(0, bi2));
						}
					}
					valsTransposed = Yin.getArray();
					vals = X.getArray();
					for(int in = 0; in < valsTransposed.length; in++) {
					    for(int jn = 0; jn < valsTransposed[in].length; jn++) {        
					        if(valsTransposed[in][jn] < 0)
					        	valsTransposed[in][jn] = -1;
					        else if (valsTransposed[in][jn] == 0) //change here
					        	valsTransposed[in][jn] = 0;
					        else
					        	valsTransposed[in][jn] = 1;
					    }
					}
					Y = new Matrix(valsTransposed);
					vals1 = Y.getArray();
					for(int in = 0; in < vals1.length; in++) {
					    for(int jn = 0; jn < vals1[in].length; jn++) {        
					        if(vals[in][jn] != vals1[in][jn])
					        {
					        ++count;
					        break;
					        }
					    }
					}
					int ans1 = 10 - count;
					try(FileWriter fw = new FileWriter("results1.txt", true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter out = new PrintWriter(bw))
						{
							out.println(ans1 + ", 3, " + i + " " + j + " " + k);    
						} catch (IOException e) {
						    System.out.println("Unable to write results to file!!!");
						}
						System.out.println("Number of Correct Classifications: " + ans + ", Vector(s) Used while Training: " + i + ", " + j + ", " + k);
				}
			}
		}
		System.out.println("=============================");
		System.out.println();
		
		//Calculate weight by using 4 input vectors at a time
		System.out.println("=============================");
		System.out.println("FINDING WEIGHTS USING 4 INPUT VECTORS...");
		System.out.println("=============================");
		for (i=0; i<7; i++)
		{
			for (j=i+1; j<8; j++)
			{
				for (k=j+1; k<9; k++)
				{
					for (int l=k+1; l<10; l++)
					{
						int count = 0;
						for (int m=0; m<35; m++)
						{
							a[m][0] = input[i][m];
							b[m][0] = input[j][m];
							c[m][0] = input[k][m];
							d[m][0] = input[l][m];
						}
						Matrix A = new Matrix(a);
						Matrix B = new Matrix(b);
						Matrix C = new Matrix(c);
						Matrix D = new Matrix(d);
						Matrix W = new Matrix(weight);
						Matrix Yin = new Matrix(output);
						Matrix Bias = new Matrix(bias);
						for(int bi=0; bi<35; bi++)
						{
							Bias.set(0, bi, A.get(bi, 0) + B.get(bi, 0) + C.get(bi, 0) + D.get(bi, 0));
						}
						W = (A).times(A.transpose());
						W = (W).plus((B).times(B.transpose()));
						W = (W).plus((C).times(C.transpose()));
						W = (W).plus((D).times(D.transpose()));
						Yin = (X.times(W));
						for(int bi=0; bi<10; bi++)
						{
							for(int bi2=0; bi2<35; bi2++)
							{
								Yin.set(bi, bi2, Yin.get(bi, bi2)+Bias.get(0, bi2));
							}
						}
						double[][] valsTransposed = Yin.getArray();
						double[][] vals = X.getArray();
						for(int in = 0; in < valsTransposed.length; in++) {
						    for(int jn = 0; jn < valsTransposed[in].length; jn++) {        
						        if(valsTransposed[in][jn] < 0)
						        	valsTransposed[in][jn] = -1;
						        else if (valsTransposed[in][jn] == 0) //change here
						        	valsTransposed[in][jn] = 0;
						        else
						        	valsTransposed[in][jn] = 1;
						    }
						}
						Matrix Y = new Matrix(valsTransposed);
						double[][]vals1 = Y.getArray();
						for(int in = 0; in < vals1.length; in++) {
						    for(int jn = 0; jn < vals1[in].length; jn++) {        
						        if(vals[in][jn] != vals1[in][jn])
						        {
						        ++count;
						        break;
						        }
						    }
						}
						int ans = 10 - count;
						try(FileWriter fw = new FileWriter("results.txt", true);
								BufferedWriter bw = new BufferedWriter(fw);
								PrintWriter out = new PrintWriter(bw))
									{
										out.println(ans + ", 4, " + i + " " + j + " " + k + " " + l);    
									} catch (IOException e) {
									    System.out.println("Unable to write results to file!!!");
									}
						count = 0; 
						for(int in=0; in<35; in++)
							W.set(in, in, 0);;
						Yin = (X.times(W));
						for(int bi=0; bi<10; bi++)
						{
							for(int bi2=0; bi2<35; bi2++)
							{
								Yin.set(bi, bi2, Yin.get(bi, bi2)+Bias.get(0, bi2));
							}
						}
						valsTransposed = Yin.getArray();
						vals = X.getArray();
						for(int in = 0; in < valsTransposed.length; in++) {
						    for(int jn = 0; jn < valsTransposed[in].length; jn++) {        
						        if(valsTransposed[in][jn] < 0)
						        	valsTransposed[in][jn] = -1;
						        else if (valsTransposed[in][jn] == 0) //change here
						        	valsTransposed[in][jn] = 0;
						        else
						        	valsTransposed[in][jn] = 1;
						    }
						}
						Y = new Matrix(valsTransposed);
						vals1 = Y.getArray();
						for(int in = 0; in < vals1.length; in++) {
						    for(int jn = 0; jn < vals1[in].length; jn++) {        
						        if(vals[in][jn] != vals1[in][jn])
						        {
						        ++count;
						        break;
						        }
						    }
						}
						int ans1 = 10 - count;
						try(FileWriter fw = new FileWriter("results1.txt", true);
						BufferedWriter bw = new BufferedWriter(fw);
						PrintWriter out = new PrintWriter(bw))
							{
								out.println(ans1 + ", 4, " + i + " " + j + " " + k + " " + l);    
							} catch (IOException e) {
							    System.out.println("Unable to write results to file!!!");
							}
							System.out.println("Number of Correct Classifications: " + ans + ", Vector(s) Used while Training: " + i + ", " + j + ", " + k + ", " + l);
					}
				}
			}
		}
		System.out.println("=============================");
		System.out.println();
					
		//Calculate weight by using 5 input vectors at a time
		System.out.println("=============================");
		System.out.println("FINDING WEIGHTS USING 5 INPUT VECTORS...");
		System.out.println("=============================");
		for (i=0; i<6; i++)
		{
			for (j=i+1; j<7; j++)
			{
				for (k=j+1; k<8; k++)
				{
					for (int l=k+1; l<9; l++)
					{
						for (int m=l+1; m<10; m++)
						{
							int count = 0;
							for (int n=0; n<35; n++)
							{
								a[n][0] = input[i][n];
								b[n][0] = input[j][n];
								c[n][0] = input[k][n];
								d[n][0] = input[l][n];
								f[n][0] = input[m][n];
							}
							Matrix A = new Matrix(a);
							Matrix B = new Matrix(b);
							Matrix C = new Matrix(c);
							Matrix D = new Matrix(d);
							Matrix E = new Matrix(f);
							Matrix W = new Matrix(weight);
							Matrix Yin = new Matrix(output);
							Matrix Bias = new Matrix(bias);
							for(int bi=0; bi<35; bi++)
							{
								Bias.set(0, bi, A.get(bi, 0) + B.get(bi, 0) + C.get(bi, 0) + D.get(bi, 0) + E.get(bi, 0));
							}
							W = (A).times(A.transpose());
							W = (W).plus((B).times(B.transpose()));
							W = (W).plus((C).times(C.transpose()));
							W = (W).plus((D).times(D.transpose()));
							W = (W).plus((E).times(E.transpose()));
							Yin = (X.times(W));
							for(int bi=0; bi<10; bi++)
							{
								for(int bi2=0; bi2<35; bi2++)
								{
									Yin.set(bi, bi2, Yin.get(bi, bi2)+Bias.get(0, bi2));
								}
							}
							double[][] valsTransposed = Yin.getArray();
							double[][] vals = X.getArray();
							for(int in = 0; in < valsTransposed.length; in++) {
							    for(int jn = 0; jn < valsTransposed[in].length; jn++) {        
							        if(valsTransposed[in][jn] < 0)
							        	valsTransposed[in][jn] = -1;
							        else if (valsTransposed[in][jn] == 0) //change here
							        	valsTransposed[in][jn] = 0;
							        else
							        	valsTransposed[in][jn] = 1;
							    }
							}
							Matrix Y = new Matrix(valsTransposed);
							double[][]vals1 = Y.getArray();
							for(int in = 0; in < vals1.length; in++) {
							    for(int jn = 0; jn < vals1[in].length; jn++) {        
							        if(vals[in][jn] != vals1[in][jn])
							        {
							        ++count;
							        break;
							        }
							    }
							}
							int ans = 10 - count;
							try(FileWriter fw = new FileWriter("results.txt", true);
									BufferedWriter bw = new BufferedWriter(fw);
									PrintWriter out = new PrintWriter(bw))
										{
											out.println(ans + ", 5, " + i + " " + j + " " + k + " " + l + " " + m);    
										} catch (IOException e) {
										    System.out.println("Unable to write results to file!!!");
										}
							count = 0; 
							for(int in=0; in<35; in++)
								W.set(in, in, 0);;
							Yin = (X.times(W));
							for(int bi=0; bi<10; bi++)
							{
								for(int bi2=0; bi2<35; bi2++)
								{
									Yin.set(bi, bi2, Yin.get(bi, bi2)+Bias.get(0, bi2));
								}
							}
							valsTransposed = Yin.getArray();
							vals = X.getArray();
							for(int in = 0; in < valsTransposed.length; in++) {
							    for(int jn = 0; jn < valsTransposed[in].length; jn++) {        
							        if(valsTransposed[in][jn] < 0)
							        	valsTransposed[in][jn] = -1;
							        else if (valsTransposed[in][jn] == 0) //change here
							        	valsTransposed[in][jn] = 0;
							        else
							        	valsTransposed[in][jn] = 1;
							    }
							}
							Y = new Matrix(valsTransposed);
							vals1 = Y.getArray();
							for(int in = 0; in < vals1.length; in++) {
							    for(int jn = 0; jn < vals1[in].length; jn++) {        
							        if(vals[in][jn] != vals1[in][jn])
							        {
							        ++count;
							        break;
							        }
							    }
							}
							int ans1 = 10 - count;
							try(FileWriter fw = new FileWriter("results1.txt", true);
							BufferedWriter bw = new BufferedWriter(fw);
							PrintWriter out = new PrintWriter(bw))
								{
									out.println(ans1 + ", 5, " + i + " " + j + " " + k + " " + l + " " + m);    
								} catch (IOException e) {
								    System.out.println("Unable to write results to file!!!");
								}
								System.out.println("Number of Correct Classifications: " + ans + ", Vector(s) Used while Training: " + i + ", " + j + ", " + k + ", " + l + ", " + m);
						}
					}
				}
			}
		}
		System.out.println("=============================");
		System.out.println();
		
		//Calculate weight by using 6 input vectors at a time
		System.out.println("=============================");
		System.out.println("FINDING WEIGHTS USING 6 INPUT VECTORS...");
		System.out.println("=============================");
		for (i=0; i<5; i++)
		{
			for (j=i+1; j<6; j++)
			{
				for (k=j+1; k<7; k++)
				{
					for (int l=k+1; l<8; l++)
					{
						for (int m=l+1; m<9; m++)
						{
							for (int n=m+1; n<10; n++)
							{
								int count = 0;
								for (int p=0; p<35; p++)
								{
									a[p][0] = input[i][p];
									b[p][0] = input[j][p];
									c[p][0] = input[k][p];
									d[p][0] = input[l][p];
									f[p][0] = input[m][p];
									g[p][0] = input[n][p];
								}
								Matrix A = new Matrix(a);
								Matrix B = new Matrix(b);
								Matrix C = new Matrix(c);
								Matrix D = new Matrix(d);
								Matrix E = new Matrix(f);
								Matrix F = new Matrix(g);
								Matrix W = new Matrix(weight);
								Matrix Yin = new Matrix(output);
								Matrix Bias = new Matrix(bias);
								for(int bi=0; bi<35; bi++)
								{
									Bias.set(0, bi, A.get(bi, 0) + B.get(bi, 0) + C.get(bi, 0) + D.get(bi, 0) + E.get(bi, 0) + F.get(bi,  0));
								}
								W = (A).times(A.transpose());
								W = (W).plus((B).times(B.transpose()));
								W = (W).plus((C).times(C.transpose()));
								W = (W).plus((D).times(D.transpose()));
								W = (W).plus((E).times(E.transpose()));
								W = (W).plus((F).times(F.transpose()));
								Yin = (X.times(W));
								for(int bi=0; bi<10; bi++)
								{
									for(int bi2=0; bi2<35; bi2++)
									{
										Yin.set(bi, bi2, Yin.get(bi, bi2)+Bias.get(0, bi2));
									}
								}
								double[][] valsTransposed = Yin.getArray();
								double[][] vals = X.getArray();
								for(int in = 0; in < valsTransposed.length; in++) {
								    for(int jn = 0; jn < valsTransposed[in].length; jn++) {        
								        if(valsTransposed[in][jn] < 0)
								        	valsTransposed[in][jn] = -1;
								        else if (valsTransposed[in][jn] == 0) //change here
								        	valsTransposed[in][jn] = 0;
								        else
								        	valsTransposed[in][jn] = 1;
								    }
								}
								Matrix Y = new Matrix(valsTransposed);
								double[][]vals1 = Y.getArray();
								for(int in = 0; in < vals1.length; in++) {
								    for(int jn = 0; jn < vals1[in].length; jn++) {        
								        if(vals[in][jn] != vals1[in][jn])
								        {
								        ++count;
								        break;
								        }
								    }
								}
								int ans = 10 - count;
								try(FileWriter fw = new FileWriter("results.txt", true);
										BufferedWriter bw = new BufferedWriter(fw);
										PrintWriter out = new PrintWriter(bw))
											{
												out.println(ans + ", 6, " + i + " " + j + " " + k + " " + l + " " + m + " " + n);    
											} catch (IOException e) {
											    System.out.println("Unable to write results to file!!!");
											}
								count = 0; 
								for(int in=0; in<35; in++)
									W.set(in, in, 0);;
								Yin = (X.times(W));
								for(int bi=0; bi<10; bi++)
								{
									for(int bi2=0; bi2<35; bi2++)
									{
										Yin.set(bi, bi2, Yin.get(bi, bi2)+Bias.get(0, bi2));
									}
								}
								valsTransposed = Yin.getArray();
								vals = X.getArray();
								for(int in = 0; in < valsTransposed.length; in++) {
								    for(int jn = 0; jn < valsTransposed[in].length; jn++) {        
								        if(valsTransposed[in][jn] < 0)
								        	valsTransposed[in][jn] = -1;
								        else if (valsTransposed[in][jn] == 0) //change here
								        	valsTransposed[in][jn] = 0;
								        else
								        	valsTransposed[in][jn] = 1;
								    }
								}
								Y = new Matrix(valsTransposed);
								vals1 = Y.getArray();
								for(int in = 0; in < vals1.length; in++) {
								    for(int jn = 0; jn < vals1[in].length; jn++) {        
								        if(vals[in][jn] != vals1[in][jn])
								        {
								        ++count;
								        break;
								        }
								    }
								}
								int ans1 = 10 - count;
								try(FileWriter fw = new FileWriter("results1.txt", true);
								BufferedWriter bw = new BufferedWriter(fw);
								PrintWriter out = new PrintWriter(bw))
									{
										out.println(ans1 + ", 6, " + i + " " + j + " " + k + " " + l + " " + m + " " + n);    
									} catch (IOException e) {
									    System.out.println("Unable to write results to file!!!");
									}
									System.out.println("Number of Correct Classifications: " + ans + ", Vector(s) Used while Training: " + i + ", " + j + ", " + k + ", " + l + ", " + m + ", " + n);
							}
						}
					}
				}
			}
		}
		System.out.println("=============================");
		System.out.println();
		
		//Calculate weight by using 7 input vectors at a time
		System.out.println("=============================");
		System.out.println("FINDING WEIGHTS USING 7 INPUT VECTORS...");
		System.out.println("=============================");
		for (i=0; i<4; i++)
		{
			for (j=i+1; j<5; j++)
			{
				for (k=j+1; k<6; k++)
				{
					for (int l=k+1; l<7; l++)
					{
						for (int m=l+1; m<8; m++)
						{
							for (int n=m+1; n<9; n++)
							{
								for (int o=n+1; o<10; o++)
								{
									int count = 0;
									for (int p1=0; p1<35; p1++)
									{
										a[p1][0] = input[i][p1];
										b[p1][0] = input[j][p1];
										c[p1][0] = input[k][p1];
										d[p1][0] = input[l][p1];
										f[p1][0] = input[m][p1];
										g[p1][0] = input[n][p1];
										h[p1][0] = input[o][p1];
									}
									Matrix A = new Matrix(a);
									Matrix B = new Matrix(b);
									Matrix C = new Matrix(c);
									Matrix D = new Matrix(d);
									Matrix E = new Matrix(f);
									Matrix F = new Matrix(g);
									Matrix G = new Matrix(h);
									Matrix W = new Matrix(weight);
									Matrix Yin = new Matrix(output);
									Matrix Bias = new Matrix(bias);
									for(int bi=0; bi<35; bi++)
									{
										Bias.set(0, bi, A.get(bi, 0) + B.get(bi, 0) + C.get(bi, 0) + D.get(bi, 0) + E.get(bi, 0) + F.get(bi,  0) + G.get(bi, 0));
									}
									W = (A).times(A.transpose());
									W = (W).plus((B).times(B.transpose()));
									W = (W).plus((C).times(C.transpose()));
									W = (W).plus((D).times(D.transpose()));
									W = (W).plus((E).times(E.transpose()));
									W = (W).plus((F).times(F.transpose()));
									W = (W).plus((G).times(G.transpose()));
									Yin = (X.times(W));
									for(int bi=0; bi<10; bi++)
									{
										for(int bi2=0; bi2<35; bi2++)
										{
											Yin.set(bi, bi2, Yin.get(bi, bi2)+Bias.get(0, bi2));
										}
									}
									double[][] valsTransposed = Yin.getArray();
									double[][] vals = X.getArray();
									for(int in = 0; in < valsTransposed.length; in++) {
									    for(int jn = 0; jn < valsTransposed[in].length; jn++) {        
									        if(valsTransposed[in][jn] < 0)
									        	valsTransposed[in][jn] = -1;
									        else if (valsTransposed[in][jn] == 0) //change here
									        	valsTransposed[in][jn] = 0;
									        else
									        	valsTransposed[in][jn] = 1;
									    }
									}
									Matrix Y = new Matrix(valsTransposed);
									double[][]vals1 = Y.getArray();
									for(int in = 0; in < vals1.length; in++) {
									    for(int jn = 0; jn < vals1[in].length; jn++) {        
									        if(vals[in][jn] != vals1[in][jn])
									        {
									        ++count;
									        break;
									        }
									    }
									}
									int ans = 10 - count;
									try(FileWriter fw = new FileWriter("results.txt", true);
											BufferedWriter bw = new BufferedWriter(fw);
											PrintWriter out = new PrintWriter(bw))
												{
													out.println(ans + ", 7, " + i + " " + j + " " + k + " " + l + " " + m + " " + n + " " + o);    
												} catch (IOException e) {
												    System.out.println("Unable to write results to file!!!");
												}
									count = 0; 
									for(int in=0; in<35; in++)
										W.set(in, in, 0);;
									Yin = (X.times(W));
									for(int bi=0; bi<10; bi++)
									{
										for(int bi2=0; bi2<35; bi2++)
										{
											Yin.set(bi, bi2, Yin.get(bi, bi2)+Bias.get(0, bi2));
										}
									}
									valsTransposed = Yin.getArray();
									vals = X.getArray();
									for(int in = 0; in < valsTransposed.length; in++) {
									    for(int jn = 0; jn < valsTransposed[in].length; jn++) {        
									        if(valsTransposed[in][jn] < 0)
									        	valsTransposed[in][jn] = -1;
									        else if (valsTransposed[in][jn] == 0) //change here
									        	valsTransposed[in][jn] = 0;
									        else
									        	valsTransposed[in][jn] = 1;
									    }
									}
									Y = new Matrix(valsTransposed);
									vals1 = Y.getArray();
									for(int in = 0; in < vals1.length; in++) {
									    for(int jn = 0; jn < vals1[in].length; jn++) {        
									        if(vals[in][jn] != vals1[in][jn])
									        {
									        ++count;
									        break;
									        }
									    }
									}
									int ans1 = 10 - count;
									try(FileWriter fw = new FileWriter("results1.txt", true);
									BufferedWriter bw = new BufferedWriter(fw);
									PrintWriter out = new PrintWriter(bw))
										{
											out.println(ans1 + ", 7, " + i + " " + j + " " + k + " " + l + " " + m + " " + n + " " + o);    
										} catch (IOException e) {
										    System.out.println("Unable to write results to file!!!");
										}
										System.out.println("Number of Correct Classifications: " + ans + ", Vector(s) Used while Training: " + i + ", " + j + ", " + k + ", " + l + ", " + m + ", " + n + ", " + o);
								}
							}
						}
					}
				}
			}
		}
		System.out.println("=============================");
		System.out.println();
		
		//Calculate weight by using 8 input vectors at a time
		System.out.println("=============================");
		System.out.println("FINDING WEIGHTS USING 8 INPUT VECTORS...");
		System.out.println("=============================");
		for (i=0; i<3; i++)
		{
			for (j=i+1; j<4; j++)
			{
				for (k=j+1; k<5; k++)
				{
					for (int l=k+1; l<6; l++)
					{
						for (int m=l+1; m<7; m++)
						{
							for (int n=m+1; n<8; n++)
							{
								for (int o=n+1; o<9; o++)
								{
									for (int p=o+1; p<10; p++)
									{
										int count = 0;
										for (int p1=0; p1<35; p1++)
										{
											a[p1][0] = input[i][p1];
											b[p1][0] = input[j][p1];
											c[p1][0] = input[k][p1];
											d[p1][0] = input[l][p1];
											f[p1][0] = input[m][p1];
											g[p1][0] = input[n][p1];
											h[p1][0] = input[o][p1];
											h1[p1][0] = input[p][p1];
										}
										Matrix A = new Matrix(a);
										Matrix B = new Matrix(b);
										Matrix C = new Matrix(c);
										Matrix D = new Matrix(d);
										Matrix E = new Matrix(f);
										Matrix F = new Matrix(g);
										Matrix G = new Matrix(h);
										Matrix H = new Matrix(h1);
										Matrix W = new Matrix(weight);
										Matrix Yin = new Matrix(output);
										Matrix Bias = new Matrix(bias);
										for(int bi=0; bi<35; bi++)
										{
											Bias.set(0, bi, A.get(bi, 0) + B.get(bi, 0) + C.get(bi, 0) + D.get(bi, 0) + E.get(bi, 0) + F.get(bi,  0) + G.get(bi, 0) + H.get(bi, 0));
										}
										W = (A).times(A.transpose());
										W = (W).plus((B).times(B.transpose()));
										W = (W).plus((C).times(C.transpose()));
										W = (W).plus((D).times(D.transpose()));
										W = (W).plus((E).times(E.transpose()));
										W = (W).plus((F).times(F.transpose()));
										W = (W).plus((G).times(G.transpose()));
										W = (W).plus((H).times(H.transpose()));
										Yin = (X.times(W));
										for(int bi=0; bi<10; bi++)
										{
											for(int bi2=0; bi2<35; bi2++)
											{
												Yin.set(bi, bi2, Yin.get(bi, bi2)+Bias.get(0, bi2));
											}
										}
										double[][] valsTransposed = Yin.getArray();
										double[][] vals = X.getArray();
										for(int in = 0; in < valsTransposed.length; in++) {
										    for(int jn = 0; jn < valsTransposed[in].length; jn++) {        
										        if(valsTransposed[in][jn] < 0)
										        	valsTransposed[in][jn] = -1;
										        else if (valsTransposed[in][jn] == 0) //change here
										        	valsTransposed[in][jn] = 0;
										        else
										        	valsTransposed[in][jn] = 1;
										    }
										}
										Matrix Y = new Matrix(valsTransposed);
										double[][]vals1 = Y.getArray();
										for(int in = 0; in < vals1.length; in++) {
										    for(int jn = 0; jn < vals1[in].length; jn++) {        
										        if(vals[in][jn] != vals1[in][jn])
										        {
										        ++count;
										        break;
										        }
										    }
										}
										int ans = 10 - count;
										try(FileWriter fw = new FileWriter("results.txt", true);
												BufferedWriter bw = new BufferedWriter(fw);
												PrintWriter out = new PrintWriter(bw))
													{
														out.println(ans + ", 8, " + i + " " + j + " " + k + " " + l + " " + m + " " + n + " " + o + " " + p);    
													} catch (IOException e) {
													    System.out.println("Unable to write results to file!!!");
													}
										count = 0; 
										for(int in=0; in<35; in++)
											W.set(in, in, 0);;
										Yin = (X.times(W));
										for(int bi=0; bi<10; bi++)
										{
											for(int bi2=0; bi2<35; bi2++)
											{
												Yin.set(bi, bi2, Yin.get(bi, bi2)+Bias.get(0, bi2));
											}
										}
										valsTransposed = Yin.getArray();
										vals = X.getArray();
										for(int in = 0; in < valsTransposed.length; in++) {
										    for(int jn = 0; jn < valsTransposed[in].length; jn++) {        
										        if(valsTransposed[in][jn] < 0)
										        	valsTransposed[in][jn] = -1;
										        else if (valsTransposed[in][jn] == 0) //change here
										        	valsTransposed[in][jn] = 0;
										        else
										        	valsTransposed[in][jn] = 1;
										    }
										}
										Y = new Matrix(valsTransposed);
										vals1 = Y.getArray();
										for(int in = 0; in < vals1.length; in++) {
										    for(int jn = 0; jn < vals1[in].length; jn++) {        
										        if(vals[in][jn] != vals1[in][jn])
										        {
										        ++count;
										        break;
										        }
										    }
										}
										int ans1 = 10 - count;
										try(FileWriter fw = new FileWriter("results1.txt", true);
										BufferedWriter bw = new BufferedWriter(fw);
										PrintWriter out = new PrintWriter(bw))
											{
												out.println(ans1 + ", 8, " + i + " " + j + " " + k + " " + l + " " + m + " " + n + " " + o + " " + p);    
											} catch (IOException e) {
											    System.out.println("Unable to write results to file!!!");
											}
											System.out.println("Number of Correct Classifications: " + ans + ", Vector(s) Used while Training: " + i + ", " + j + ", " + k + ", " + l + ", " + m + ", " + n + ", " + o + ", " + p);
									}
								}
							}
						}
					}
				}
			}
		}
		System.out.println("=============================");
		System.out.println();
		
		//Calculate weight by using 9 input vectors at a time
		System.out.println("=============================");
		System.out.println("FINDING WEIGHTS USING 9 INPUT VECTORS...");
		System.out.println("=============================");
		for (i=0; i<2; i++)
		{
			for (j=i+1; j<3; j++)
			{
				for (k=j+1; k<4; k++)
				{
					for (int l=k+1; l<5; l++)
					{
						for (int m=l+1; m<6; m++)
						{
							for (int n=m+1; n<7; n++)
							{
								for (int o=n+1; o<8; o++)
								{
									for (int p=o+1; p<9; p++)
									{
										for (int p1=p+1; p1<10; p1++)
										{
											int count = 0;
											for (int p2=0; p2<35; p2++)
											{
												a[p2][0] = input[i][p2];
												b[p2][0] = input[j][p2];
												c[p2][0] = input[k][p2];
												d[p2][0] = input[l][p2];
												f[p2][0] = input[m][p2];
												g[p2][0] = input[n][p2];
												h[p2][0] = input[o][p2];
												h1[p2][0] = input[p][p2];
												h2[p2][0] = input[p1][p2];
											}
											Matrix A = new Matrix(a);
											Matrix B = new Matrix(b);
											Matrix C = new Matrix(c);
											Matrix D = new Matrix(d);
											Matrix E = new Matrix(f);
											Matrix F = new Matrix(g);
											Matrix G = new Matrix(h);
											Matrix H = new Matrix(h1);
											Matrix I = new Matrix(h2);
											Matrix W = new Matrix(weight);
											Matrix Yin = new Matrix(output);
											Matrix Bias = new Matrix(bias);
											for(int bi=0; bi<35; bi++)
											{
												Bias.set(0, bi, A.get(bi, 0) + B.get(bi, 0) + C.get(bi, 0) + D.get(bi, 0) + E.get(bi, 0) + F.get(bi,  0) + G.get(bi, 0) + H.get(bi, 0) + I.get(bi, 0));
											}
											W = (A).times(A.transpose());
											W = (W).plus((B).times(B.transpose()));
											W = (W).plus((C).times(C.transpose()));
											W = (W).plus((D).times(D.transpose()));
											W = (W).plus((E).times(E.transpose()));
											W = (W).plus((F).times(F.transpose()));
											W = (W).plus((G).times(G.transpose()));
											W = (W).plus((H).times(H.transpose()));
											W = (W).plus((I).times(I.transpose()));
											Yin = (X.times(W));
											for(int bi=0; bi<10; bi++)
											{
												for(int bi2=0; bi2<35; bi2++)
												{
													Yin.set(bi, bi2, Yin.get(bi, bi2)+Bias.get(0, bi2));
												}
											}
											double[][] valsTransposed = Yin.getArray();
											double[][] vals = X.getArray();
											for(int in = 0; in < valsTransposed.length; in++) {
											    for(int jn = 0; jn < valsTransposed[in].length; jn++) {        
											        if(valsTransposed[in][jn] < 0)
											        	valsTransposed[in][jn] = -1;
											        else if (valsTransposed[in][jn] == 0) //change here
											        	valsTransposed[in][jn] = 0;
											        else
											        	valsTransposed[in][jn] = 1;
											    }
											}
											Matrix Y = new Matrix(valsTransposed);
											double[][]vals1 = Y.getArray();
											for(int in = 0; in < vals1.length; in++) {
											    for(int jn = 0; jn < vals1[in].length; jn++) {        
											        if(vals[in][jn] != vals1[in][jn])
											        {
											        ++count;
											        break;
											        }
											    }
											}
											int ans = 10 - count;
											try(FileWriter fw = new FileWriter("results.txt", true);
													BufferedWriter bw = new BufferedWriter(fw);
													PrintWriter out = new PrintWriter(bw))
														{
															out.println(ans + ", 9, " + i + " " + j + " " + k + " " + l + " " + m + " " + n + " " + o + " " + p + " " + p1);    
														} catch (IOException e) {
														    System.out.println("Unable to write results to file!!!");
														}
											count = 0; 
											for(int in=0; in<35; in++)
												W.set(in, in, 0);;
											Yin = (X.times(W));
											for(int bi=0; bi<10; bi++)
											{
												for(int bi2=0; bi2<35; bi2++)
												{
													Yin.set(bi, bi2, Yin.get(bi, bi2)+Bias.get(0, bi2));
												}
											}
											valsTransposed = Yin.getArray();
											vals = X.getArray();
											for(int in = 0; in < valsTransposed.length; in++) {
											    for(int jn = 0; jn < valsTransposed[in].length; jn++) {        
											        if(valsTransposed[in][jn] < 0)
											        	valsTransposed[in][jn] = -1;
											        else if (valsTransposed[in][jn] == 0) //change here
											        	valsTransposed[in][jn] = 0;
											        else
											        	valsTransposed[in][jn] = 1;
											    }
											}
											Y = new Matrix(valsTransposed);
											vals1 = Y.getArray();
											for(int in = 0; in < vals1.length; in++) {
											    for(int jn = 0; jn < vals1[in].length; jn++) {        
											        if(vals[in][jn] != vals1[in][jn])
											        {
											        ++count;
											        break;
											        }
											    }
											}
											int ans1 = 10 - count;
											try(FileWriter fw = new FileWriter("results1.txt", true);
											BufferedWriter bw = new BufferedWriter(fw);
											PrintWriter out = new PrintWriter(bw))
												{
													out.println(ans1 + ", 9, " + i + " " + j + " " + k + " " + l + " " + m + " " + n + " " + o + " " + p + " " + p1);    
												} catch (IOException e) {
												    System.out.println("Unable to write results to file!!!");
												}
												System.out.println("Number of Correct Classifications: " + ans + ", Vector(s) Used while Training: " + i + ", " + j + ", " + k + ", " + l + ", " + m + ", " + n + ", " + o + ", " + p + ", " + p1);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		System.out.println("=============================");
		System.out.println();
		
		//Calculate weight by using 10 input vectors at a time
		System.out.println("=============================");
		System.out.println("FINDING WEIGHTS USING 10 INPUT VECTORS...");
		System.out.println("=============================");
		int count = 0;
		Matrix W = new Matrix(weight);
		W = (X.transpose()).times(X);
		Matrix Yin = new Matrix(output);
		Matrix Bias = new Matrix(bias);
		for(int bi=0; bi<35; bi++)
		{
			for(int bi2=0; bi2<10; bi2++)
			{
				Bias.set(0, bi, Bias.get(0, bi)+X.get(bi2, bi));
			}
		}
		Yin = (X.times(W));
		for(int bi=0; bi<10; bi++)
		{
			for(int bi2=0; bi2<35; bi2++)
			{
				Yin.set(bi, bi2, Yin.get(bi, bi2)+Bias.get(0, bi2));
			}
		}
		double[][] valsTransposed = Yin.getArray();
		double[][] vals = X.getArray();
		for(i = 0; i < valsTransposed.length; i++) {
		    for(j = 0; j < valsTransposed[i].length; j++) {        
		        if(valsTransposed[i][j] < 0)
		        	valsTransposed[i][j] = -1;
		        else if (valsTransposed[i][j] == 0) //change here
		        	valsTransposed[i][j] = 0;
		        else
		        	valsTransposed[i][j] = 1;
		    }
		}
		Matrix Y = new Matrix(valsTransposed);
		double[][]vals1 = Y.getArray();
		for(int in = 0; in < vals1.length; in++) {
		    for(int jn = 0; jn < vals1[in].length; jn++) {        
		        if(vals[in][jn] != vals1[in][jn])
		        {
		        ++count;
		        break;
		        }
		    }
		}
		int ans = 10 - count;
		try(FileWriter fw = new FileWriter("results.txt", true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter out = new PrintWriter(bw))
			{
				out.println(ans + ", 10, 0 1 2 3 4 5 6 7 8 9");    
			} catch (IOException e) {
			    System.out.println("Unable to write results to file!!!");
			}
		count = 0; 
		for(int in=0; in<35; in++)
			W.set(in, in, 0);;
		Yin = (X.times(W));
		for(int bi=0; bi<10; bi++)
		{
			for(int bi2=0; bi2<35; bi2++)
			{
				Yin.set(bi, bi2, Yin.get(bi, bi2)+Bias.get(0, bi2));
			}
		}
		valsTransposed = Yin.getArray();
		vals = X.getArray();
		for(int in = 0; in < valsTransposed.length; in++) {
		    for(int jn = 0; jn < valsTransposed[in].length; jn++) {        
		        if(valsTransposed[in][jn] < 0)
		        	valsTransposed[in][jn] = -1;
		        else if (valsTransposed[in][jn] == 0) //change here
		        	valsTransposed[in][jn] = 0;
		        else
		        	valsTransposed[in][jn] = 1;
		    }
		}
		Y = new Matrix(valsTransposed);
		vals1 = Y.getArray();
		for(int in = 0; in < vals1.length; in++) {
		    for(int jn = 0; jn < vals1[in].length; jn++) {        
		        if(vals[in][jn] != vals1[in][jn])
		        {
		        ++count;
		        break;
		        }
		    }
		}
		int ans1 = 10 - count;
		try(FileWriter fw = new FileWriter("results1.txt", true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter out = new PrintWriter(bw))
			{
				out.println(ans1 + ", 10, 0 1 2 3 4 5 6 7 8 9");    
			} catch (IOException e) {
			    System.out.println("Unable to write results to file!!!");
			}
	System.out.println("Number of Correct Classifications: " + ans + ", Vector(s) Used while Training: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9");
	System.out.println("=============================");
	System.out.println();
	
	//Finding maximum number of patterns stored and recalled successfully
	System.out.println("=============================");
	System.out.println("MAXIMUM NUMBER OF PATTERNS STORED AND RECALLED SUCCESSFULLY");
	System.out.println("=============================");
	try{
		scan = new Scanner(new File("results.txt"));
	}
	catch(Exception e)
	{
		System.out.println("Unable to open results File!!");
	}
	int maxPatterns = 0;
	while((scan.hasNextLine()))
	{
		String line = scan.nextLine();
		char ch = line.charAt(0);
		if (Character.getNumericValue(ch) > maxPatterns)
			maxPatterns = Character.getNumericValue(ch);
	}
	System.out.println("Maximum Number of Patterns Stored and Recalled Successfully: " + maxPatterns);
	System.out.println();
	try{
		scan = new Scanner(new File("results.txt"));
	}
	catch(Exception e)
	{
		System.out.println("Unable to open results File!!");
	}
	int count1 = 1;
	ArrayList<Integer> list = new ArrayList<Integer>();
	while((scan.hasNextLine()))
	{
		String line = scan.nextLine();
		char ch = line.charAt(0);
		if (Character.getNumericValue(ch) == maxPatterns)
		{
			char ch1 = line.charAt(3);
			if (Character.getNumericValue(ch1) == maxPatterns)
			{
				System.out.println();
				System.out.print("Choice: " + count1);
				System.out.println();
				System.out.print("---------");
				System.out.println();
				System.out.print("Number of Patterns used while Training: " + Character.getNumericValue(ch1));
				System.out.println();
				System.out.print("Patterns Stored and Recalled Sucessfully: ");
				for(i=0; i<Character.getNumericValue(ch1); i++)
				{
					System.out.print(Character.getNumericValue(line.charAt(6+(2*i))) + " ");
					list.add(Character.getNumericValue(line.charAt(6+(2*i))));
				}
				System.out.println();
				count1++;
			}
		}
	}
	System.out.println("=============================");
	System.out.println();
	
	//Finding maximum number of patterns stored and recalled successfully after zeroing out the diagonal elements for the above weight choices
	ArrayList<Integer> list1 = new ArrayList<Integer>();
	System.out.println("=============================");
	System.out.println("CHECKING RESPONSE WITH ZERO DIAGONAL WEIGHT MATRICES");
	System.out.println("=============================");
	try{
		scan = new Scanner(new File("results1.txt"));
	}
	catch(Exception e)
	{
		System.out.println("Unable to open results File!!");
	}
	count1 = 1;
	i=0;
	while((scan.hasNextLine()))
	{
		int maxPatterns1 = 0;
		String line = scan.nextLine();
		if (Character.getNumericValue(line.charAt(3)) == maxPatterns && Character.getNumericValue(line.charAt(6)) == list.get(i) && Character.getNumericValue(line.charAt(8)) == list.get(i+1) && Character.getNumericValue(line.charAt(10)) == list.get(i+2) && Character.getNumericValue(line.charAt(12)) == list.get(i+3) && Character.getNumericValue(line.charAt(14)) == list.get(i+4))
		{
			maxPatterns1 = Character.getNumericValue(line.charAt(0));
			System.out.println();
			System.out.print("Choice: " + count1);
			System.out.println();
			System.out.print("---------");
			System.out.println();
			System.out.print("Number of Patterns Stored and Recalled Successfully: " + maxPatterns1);
			System.out.println();
			System.out.print("Patterns Stored and Recalled Sucessfully: ");
			for(int in=0; in<maxPatterns; in++)
			{
				System.out.print(Character.getNumericValue(line.charAt(6+(2*in))) + " ");
			}
			list1.add(maxPatterns1);
			System.out.println();
			count1++;
			i=i+5;
			if(i==list.size())
				break;
		}
	}
	System.out.println();
	System.out.print("Better Weight Choices: ");
	for(i=0; i<list1.size(); i++)
	{
		if(maxPatterns == list1.get(i))
		{
			System.out.print(i+1);
			System.out.print(" ");
		}
	}
	System.out.print("as even after zeroing out the diagonal elements of the weight matrix, they ");
	System.out.println();
	System.out.println("still classify all the stored patterns successfully.");
	System.out.println("=============================");
	System.out.println();
	
	//Checking Correlation among the Patterns of the 6 Choices
	System.out.println("=============================");
	System.out.println("CHECKING ROBUSTNESS");
	System.out.println("=============================");
	double [][] choice1 = new double [maxPatterns][35];
	double [][] choice2 = new double [maxPatterns][35];
	double [][] choice3 = new double [maxPatterns][35];
	double [][] choice4 = new double [maxPatterns][35];
	double [][] choice5 = new double [maxPatterns][35];
	double [][] choice6 = new double [maxPatterns][35];
	double [][] choice7 = new double [maxPatterns][35];
	double [][] choice8 = new double [maxPatterns][35];
	double [][] choice9 = new double [maxPatterns][35];
	double [][] choice10 = new double [maxPatterns][35];
	double [][] choice11 = new double [maxPatterns][35];
	double [][] choice12 = new double [maxPatterns][35];
	double [][] choice13 = new double [maxPatterns][35];
	double [][] choice14 = new double [maxPatterns][35];
	double [][] choice15 = new double [maxPatterns][35];
	double [][] choice16 = new double [maxPatterns][35];
	double [][] choice17 = new double [maxPatterns][35];
	double [][] choice18 = new double [maxPatterns][35];
	double [][] choice19 = new double [maxPatterns][35];
	double [][] choice20 = new double [maxPatterns][35];
	double [][] choice21 = new double [maxPatterns][35];
	double [][] choice22 = new double [maxPatterns][35];
	double [][] choice23 = new double [maxPatterns][35];
	int index = 0;
	for(i=0; i<maxPatterns; i++)
	{
		for(j=0; j<35; j++)
		{
			choice1[index][j] = input1 [list.get(i)][j];
		}
		index++;
	}
	index = 0;
	for(i=maxPatterns; i<(2*maxPatterns); i++)
	{
		for(j=0; j<35; j++)
		{
			choice2[index][j] = input1 [list.get(i)][j];
			choice8[index][j] = input1 [list.get(i)][j];
			choice10[index][j] = input1 [list.get(i)][j];
			choice14[index][j] = input1 [list.get(i)][j];
			choice15[index][j] = input1 [list.get(i)][j];
			choice16[index][j] = input1 [list.get(i)][j];
			choice17[index][j] = input1 [list.get(i)][j];
			choice21[index][j] = input1 [list.get(i)][j];
			choice22[index][j] = input1 [list.get(i)][j];
			choice23[index][j] = input1 [list.get(i)][j];
		}
		index++;
	}
	index = 0;
	for(i=(6*maxPatterns); i<(7*maxPatterns); i++)
	{
		for(j=0; j<35; j++)
		{
			choice3[index][j] = input1 [list.get(i)][j];
		}
		index++;
	}
	index = 0;
	for(i=(7*maxPatterns); i<(8*maxPatterns); i++)
	{
		for(j=0; j<35; j++)
		{
			choice4[index][j] = input1 [list.get(i)][j];
		}
		index++;
	}
	index = 0;
	for(i=(9*maxPatterns); i<(10*maxPatterns); i++)
	{
		for(j=0; j<35; j++)
		{
			choice5[index][j] = input1 [list.get(i)][j];
		}
		index++;
	}
	index = 0;
	for(i=(12*maxPatterns); i<(13*maxPatterns); i++)
	{
		for(j=0; j<35; j++)
		{
			choice6[index][j] = input1 [list.get(i)][j];
			choice7[index][j] = input1 [list.get(i)][j];
			choice9[index][j] = input1 [list.get(i)][j];
			choice11[index][j] = input1 [list.get(i)][j];
			choice12[index][j] = input1 [list.get(i)][j];
			choice13[index][j] = input1 [list.get(i)][j];
			choice18[index][j] = input1 [list.get(i)][j];
			choice19[index][j] = input1 [list.get(i)][j];
			choice20[index][j] = input1 [list.get(i)][j];
		}
		index++;
	}
	double [][] corelation = new double [maxPatterns][maxPatterns];
	int sum = 0;
	System.out.print("Choice: 1 (0 1 2 4 7)");
	System.out.println();
	System.out.println("---------------------");
	Matrix Choice1 = new Matrix(choice1);
	Matrix Corelation = new Matrix(corelation);
	Corelation = Choice1.times(Choice1.transpose());
	for(i=0; i<maxPatterns; i++)
	{
		for(j=0; j<maxPatterns; j++)
		{
			System.out.printf("%3d", (int)Corelation.get(i,j));
			sum = sum + (int)Math.abs(Corelation.get(i,j));
			System.out.print(" ");
		}
		System.out.println();
	}
	System.out.println("Absolute Sum of Correlations: " + sum);
	sum = 0;
	System.out.println();
	System.out.print("Choice: 2 (0 1 3 4 7)");
	System.out.println();
	System.out.println("---------------------");
	Matrix Choice2 = new Matrix(choice2);
	Corelation = new Matrix(corelation);
	Corelation = Choice2.times(Choice2.transpose());
	for(i=0; i<maxPatterns; i++)
	{
		for(j=0; j<maxPatterns; j++)
		{
			System.out.printf("%3d", (int)Corelation.get(i,j));
			sum = sum + (int)Math.abs(Corelation.get(i,j));
			System.out.print(" ");
		}
		System.out.println();
	}
	System.out.println("Absolute Sum of Correlations: " + sum);
	sum = 0;
	System.out.println();
	System.out.print("Choice: 7 (1 2 4 6 7)");
	System.out.println();
	System.out.println("---------------------");
	Matrix Choice3 = new Matrix(choice3);
	Corelation = new Matrix(corelation);
	Corelation = Choice3.times(Choice3.transpose());
	for(i=0; i<maxPatterns; i++)
	{
		for(j=0; j<maxPatterns; j++)
		{
			System.out.printf("%3d", (int)Corelation.get(i,j));
			sum = sum + (int)Math.abs(Corelation.get(i,j));
			System.out.print(" ");
		}
		System.out.println();
	}
	System.out.println("Absolute Sum of Correlations: " + sum);
	sum = 0;
	System.out.println();
	System.out.print("Choice: 8 (1 2 4 7 8)");
	System.out.println();
	System.out.println("---------------------");
	Matrix Choice4 = new Matrix(choice4);
	Corelation = new Matrix(corelation);
	Corelation = Choice4.times(Choice4.transpose());
	for(i=0; i<maxPatterns; i++)
	{
		for(j=0; j<maxPatterns; j++)
		{
			System.out.printf("%3d", (int)Corelation.get(i,j));
			sum = sum + (int)Math.abs(Corelation.get(i,j));
			System.out.print(" ");
		}
		System.out.println();
	}
	System.out.println("Absolute Sum of Correlations: " + sum);
	sum = 0;
	System.out.println();
	System.out.print("Choice: 10 (1 4 5 7 8)");
	System.out.println();
	System.out.println("----------------------");
	Matrix Choice5 = new Matrix(choice5);
	Corelation = new Matrix(corelation);
	Corelation = Choice5.times(Choice5.transpose());
	for(i=0; i<maxPatterns; i++)
	{
		for(j=0; j<maxPatterns; j++)
		{
			System.out.printf("%3d", (int)Corelation.get(i,j));
			sum = sum + (int)Math.abs(Corelation.get(i,j));
			System.out.print(" ");
		}
		System.out.println();
	}
	System.out.println("Absolute Sum of Correlations: " + sum);
	sum = 0;
	System.out.println();
	System.out.print("Choice: 13 (1 4 6 7 9)");
	System.out.println();
	System.out.println("----------------------");
	Matrix Choice6 = new Matrix(choice6);
	Corelation = new Matrix(corelation);
	Corelation = Choice6.times(Choice6.transpose());
	for(i=0; i<maxPatterns; i++)
	{
		for(j=0; j<maxPatterns; j++)
		{
			System.out.printf("%3d", (int)Corelation.get(i,j));
			sum = sum + (int)Math.abs(Corelation.get(i,j));
			System.out.print(" ");
		}
		System.out.println();
	}
	System.out.println("Absolute Sum of Correlations: " + sum);
	sum = 0;
	System.out.println();
	System.out.println("Best Choice       : Choice 13 (1 4 6 7 9)");
	System.out.println("Nearly Best Choice: Choice  2 (0 1 3 4 7)");
	System.out.println("=============================");
	System.out.println();
	
	//Printing Weights & Biases for the 2 Choices
	System.out.println("=============================");
	System.out.println("CALCULATING WEIGHTS & BIASES");
	System.out.println("=============================");
	System.out.println("Using Choice: 13 (1 4 6 7 9)");		
	System.out.println("----------------------------");
	System.out.println("Weight:");
	System.out.println("-------");
	Matrix Choice9 = new Matrix(choice9);
	Matrix Bias1 = new Matrix(bias13);
	Matrix W1 = new Matrix(weight);
	W1 = (Choice9.transpose()).times(Choice9);
	for(int bi=0; bi<35; bi++)
	{
		for(int bi2=0; bi2<maxPatterns; bi2++)
		{
			Bias1.set(0, bi, Bias1.get(0, bi)+Choice9.get(bi2, bi));
		}
	}
	for(i=0; i<35; i++)
	{
		for(j=0; j<35; j++)
		{
			System.out.printf("%2d", (int)W1.get(i,j));
			System.out.print(" ");
		}
		System.out.println();
	}
	System.out.println();
	System.out.println("Bias:");
	System.out.println("-----");
	for(i=0; i<35; i++)
	{
		System.out.printf("%2d", (int)Bias1.get(0,i));
		System.out.print(" ");
	}
	System.out.println();
	System.out.println();
	System.out.println("Using Choice: 2 (0 1 3 4 7)");
	System.out.println("-----------------------------");
	System.out.println("Weight:");
	System.out.println("-------");
	Matrix Choice10 = new Matrix(choice10);
	Matrix Bias2 = new Matrix(bias2);
	Matrix W2 = new Matrix(weight);
	W2 = (Choice9.transpose()).times(Choice10);
	for(int bi=0; bi<35; bi++)
	{
		for(int bi2=0; bi2<maxPatterns; bi2++)
		{
			Bias2.set(0, bi, Bias2.get(0, bi)+Choice10.get(bi2, bi));
		}
	}
	for(i=0; i<35; i++)
	{
		for(j=0; j<35; j++)
		{
			System.out.printf("%2d", (int)W2.get(i,j));
			System.out.print(" ");
		}
		System.out.println();
	}
	System.out.println();
	System.out.println("Bias:");
	System.out.println("-----");
	for(i=0; i<35; i++)
	{
		System.out.printf("%2d", (int)Bias2.get(0,i));
		System.out.print(" ");
	}
	System.out.println();
	System.out.println("=============================");
	System.out.println();
	
	//Checking orthogonal patterns
	System.out.println("=============================");
	System.out.println("CHECKING ORTHOGONAL PATTERNS");
	System.out.println("=============================");
	System.out.println("Using Choice: 13 (1 4 6 7 9)");		
	System.out.println("----------------------------");
	double check1 [][] = new double [35][1];
	double check2 [][] = new double [35][1];
	int oCount1 = 0, oCount2 = 0, oCount3 = 0, oCount4 = 0, oCount5 = 0, oCount6 = 0;
	for(i=0; i<maxPatterns-1; i++)
	{
		for(j=i+1; j<maxPatterns; j++)
		{
			for(k=0; k<35; k++)
			{
				check1[k][0] = choice7[i][k];
				check2[k][0] = choice7[j][k];
			}
			double result = 0;
			int iValue = 0, jValue = 0;
			for(int in=0; in<35; in++)
			{
				result = result + (check1[in][0] * check2[in][0]);
			}
			if(i == 0)
				iValue = 1;
			if(i == 1)
				iValue = 4;
			if(i == 2)
				iValue = 6;
			if(i == 3)
				iValue = 7;
			if(j == 1)
				jValue = 4;
			if(j == 2)
				jValue = 6;
			if(j == 3)
				jValue = 7;
			if(j == 4)
				jValue = 9;
			System.out.println("(Pattern " + iValue + ") . (Pattern " + jValue + ") = " + result);
			if(result == 0)
				oCount1++;
			if(result == 1.0 || result == -1.0)
				oCount2++;
			if(result == 2.0 || result == -2.0)
				oCount3++;
			if(result == 3.0 || result == -3.0)
				oCount4++;
			if(result == 4.0 || result == -4.0)
				oCount5++;
			if(result == 5.0 || result == -5.0)
				oCount6++;
		}
	}
	System.out.println();
	System.out.println("Number of Orthogonal Vectors (Result = 0)             : " + oCount1);
	System.out.println("Number of Nearly Orthogonal Vectors (Result = +1 / -1): " + oCount2);
	System.out.println("Number of Nearly Orthogonal Vectors (Result = +2 / -2): " + oCount3);
	System.out.println("Number of Nearly Orthogonal Vectors (Result = +3 / -3): " + oCount4);
	System.out.println("Number of Nearly Orthogonal Vectors (Result = +4 / -4): " + oCount5);
	System.out.println("Number of Nearly Orthogonal Vectors (Result = +5 / -5): " + oCount6);
	System.out.println();
	System.out.println("Using Choice: 2 (0 1 3 4 7)");
	System.out.println("-----------------------------");
	oCount1 = 0;
	oCount2 = 0;
	oCount3 = 0;
	oCount4 = 0;
	oCount5 = 0;
	oCount6 = 0;
	for(i=0; i<maxPatterns-1; i++)
	{
		for(j=i+1; j<maxPatterns; j++)
		{
			for(k=0; k<35; k++)
			{
				check1[k][0] = choice8[i][k];
				check2[k][0] = choice8[j][k];
			}
			double result = 0;
			int iValue = 0, jValue = 0;
			for(int in=0; in<35; in++)
			{
				result = result + (check1[in][0] * check2[in][0]);
			}
			if(i == 0)
				iValue = 0;
			if(i == 1)
				iValue = 1;
			if(i == 2)
				iValue = 3;
			if(i == 3)
				iValue = 4;
			if(j == 1)
				jValue = 1;
			if(j == 2)
				jValue = 3;
			if(j == 3)
				jValue = 4;
			if(j == 4)
				jValue = 7;
			System.out.println("(Pattern " + iValue + ") . (Pattern " + jValue + ") = " + result);
			if(result == 0)
				oCount1++;
			if(result == 1.0 || result == -1.0)
				oCount2++;
			if(result == 2.0 || result == -2.0)
				oCount3++;
			if(result == 3.0 || result == -3.0)
				oCount4++;
			if(result == 4.0 || result == -4.0)
				oCount5++;
			if(result == 5.0 || result == -5.0)
				oCount6++;
		}
	}
	System.out.println();
	System.out.println("Number of Orthogonal Vectors (Result = 0)             : " + oCount1);
	System.out.println("Number of Nearly Orthogonal Vectors (Result = +1 / -1): " + oCount2);
	System.out.println("Number of Nearly Orthogonal Vectors (Result = +2 / -2): " + oCount3);
	System.out.println("Number of Nearly Orthogonal Vectors (Result = +3 / -3): " + oCount4);
	System.out.println("Number of Nearly Orthogonal Vectors (Result = +4 / -4): " + oCount5);
	System.out.println("Number of Nearly Orthogonal Vectors (Result = +5 / -5): " + oCount6);
	System.out.print("=============================");
	System.out.println();
	System.out.println();
	
	//Handling Noise
	System.out.println("=============================");
	System.out.println("HANDLING NOISE IN THE NEURAL NET");
	System.out.println("=============================");
	System.out.println("Using Patterns: 1 4 6 7 9");
	System.out.println("---------------------------");
	int noisyBits = -1;
	System.out.print("Enter Number of Noisy Bits: ");
	Scanner in = new Scanner(System.in);
	while(noisyBits<0 || noisyBits>35)
		noisyBits = in.nextInt();
	System.out.println("Generating Patterns with " + noisyBits + " Noisy Bits");
	ArrayList<Integer> rand = new ArrayList<Integer>();
	ArrayList<Integer> noise = new ArrayList<Integer>();
	noise.add(-1);
	noise.add(0);
    for (i=0; i<35; i++) {
        rand.add(new Integer(i));
    }
    Matrix tempInput = new Matrix(choice11);
    Matrix tempOutput1 = new Matrix(output1);
    Matrix Choice = new Matrix(choice12);
    Matrix ChoiceB1 = new Matrix(choice13);
    Matrix Bias11 = new Matrix(bias1);
    for(int bi=0; bi<35; bi++)
	{
		for(int bi2=0; bi2<maxPatterns; bi2++)
		{
			Bias11.set(0, bi, Bias11.get(0, bi)+ChoiceB1.get(bi2, bi));
		}
	}
	Collections.shuffle(rand);
    for (i=0; i<noisyBits; i++) {
    	int noiseVal = noise.get(new Random().nextInt(noise.size()));
        for(j=0; j<5; j++)
        {
        	tempInput.set(j, rand.get(i), tempInput.get(j, rand.get(i))*noiseVal);
        }
    }
    tempOutput1 = tempInput.times(W1);
    for(i=0; i<maxPatterns; i++)
    {
    	for(j=0; j<35; j++)
    	{
    		tempOutput1.set(i, j, tempOutput1.get(i, j)+Bias11.get(0, j));
    	}
    }
    for(i=0; i<5; i++)
    {
    	for(j=0; j<35; j++)
    	{
    		if(tempOutput1.get(i, j) < 0)
    			tempOutput1.set(i, j, -1);
    		else if(tempOutput1.get(i, j) == 0)
    			tempOutput1.set(i, j, 0);
    		else
    			tempOutput1.set(i, j, 1);
    	}
    }
    System.out.println();
    
    System.out.println("Actual Input Pattern     Noisy Input Pattern     Expected Output Pattern     Obtained Output Pattern");
    System.out.println("--------------------     -------------------     -----------------------     -----------------------");
    for(i=0; i<5; i++)
    {
    	
    	for(j=0; j<35; j=j+5)
    	{
    		char ch11= ' ', ch22=' ', ch33=' ', ch44=' ';
    		for(k=0; k<5; k++)
    		{
        		if (Choice.get(i, j+k) == 1)
        			ch11 = '#';
        		else if (Choice.get(i, j+k) == 0)
        			ch22 = 'X';
        		else
        			ch11 = ' ';
        		System.out.print(ch11);
    		}
    		System.out.print("                    ");
    		for(k=0; k<5; k++)
    		{
        		if (tempInput.get(i, j+k) == 1)
        			ch22 = '#';
        		else if (tempInput.get(i, j+k) == 0)
        			ch22 = 'X';
        		else
        			ch22 = ' ';
        		System.out.print(ch22);
    		}
    		System.out.print("                    ");
    		for(k=0; k<5; k++)
    		{
        		if (Choice.get(i, j+k) == 1)
        			ch33 = '#';
        		else if (Choice.get(i, j+k) == 0)
        			ch22 = 'X';
        		else
        			ch33 = ' ';
        		System.out.print(ch33);
    		}
    		System.out.print("                       ");
    		for(k=0; k<5; k++)
    		{
        		if (tempOutput1.get(i, j+k) == 1)
        			ch44 = '#';
        		else if (tempOutput1.get(i, j+k) == 0)
        			ch22 = 'X';
        		else
        			ch44 = ' ';
        		System.out.print(ch44);
    		}
    		System.out.println();
    	}
    	System.out.println();
    }
    ArrayList<Integer> noiseList = new ArrayList<Integer>();
    ArrayList<Integer> correctList = new ArrayList<Integer>();
    int noiseRate = 0, val = -1;
    for(i = 0; i < 5; i++)  
    {
    	int check = 0;
	    for(j = 0; j < 35; j++) 
	    {        
	        if(tempOutput1.get(i, j) != Choice.get(i, j))
	        {
	        ++noiseRate;
	        if(i == 0)
	        	val = 1;
	        if(i == 1)
	        	val = 4;
	        if(i == 2)
	        	val = 6;
	        if(i == 3)
	        	val = 7;
	        if(i == 4)
	        	val = 9;
	        noiseList.add(val);
	        check = 1;
	        break;
	        } 
	    }
	    if(check == 0)
	    {
	    	if(i == 0)
	        	val = 1;
	        if(i == 1)
	        	val = 4;
	        if(i == 2)
	        	val = 6;
	        if(i == 3)
	        	val = 7;
	        if(i == 4)
	        	val = 9;
	        correctList.add(val);
	    } 
	}
	int successRate = 5 - noiseRate;
	System.out.println("Number of Patterns Correctly Recalled: " + successRate);
	System.out.print("Correct Patterns                     : ");
	for (i=0; i<correctList.size(); i++)
		System.out.print(correctList.get(i) + " ");
	System.out.println();
	System.out.print("Incorrect Patterns                   : ");
	for (i=0; i<noiseList.size(); i++)
		System.out.print(noiseList.get(i) + " ");
	System.out.println();
	System.out.println();
	System.out.println("Using Patterns: 0 1 3 4 7");
	System.out.println("---------------------------");
	int noisyBits1 = -1;
	System.out.print("Enter Number of Noisy Bits: ");
	in = new Scanner(System.in);
	while(noisyBits1<0 || noisyBits1>35)
		noisyBits1 = in.nextInt();
	System.out.println("Generating Patterns with " + noisyBits1 + " Noisy Bits");
	ArrayList<Integer> rand1 = new ArrayList<Integer>();
	ArrayList<Integer> noise1 = new ArrayList<Integer>();
	noise1.add(-1);
	noise1.add(0);
    for (i=0; i<35; i++) {
        rand1.add(new Integer(i));
    }
    Matrix tempInput1 = new Matrix(choice14);
    Matrix ChoiceB2 = new Matrix(choice15);
    Matrix ChoiceB3 = new Matrix(choice17);
    Matrix tempOutput2 = new Matrix(output11);
    Matrix Bias12 = new Matrix(bias11);
    Matrix W22 = new Matrix(weight);
    W22 = (ChoiceB3.transpose()).times(ChoiceB3);
    for(int bi=0; bi<35; bi++)
	{
		for(int bi2=0; bi2<maxPatterns; bi2++)
		{
			Bias12.set(0, bi, Bias12.get(0, bi)+ChoiceB2.get(bi2, bi));
		}
	}
	Collections.shuffle(rand1);
    for (i=0; i<noisyBits1; i++) {
    	int noiseVal = noise1.get(new Random().nextInt(noise1.size()));
        for(j=0; j<5; j++)
        {
        	tempInput1.set(j, rand1.get(i), tempInput1.get(j, rand1.get(i))*noiseVal);
        }
    }
    Matrix Choice111 = new Matrix(choice16);
    tempOutput2 = tempInput1.times(W22);
    for(i=0; i<maxPatterns; i++)
    {
    	for(j=0; j<35; j++)
    	{
    		tempOutput2.set(i, j, tempOutput2.get(i, j)+Bias12.get(0, j));
    	}
    }
    for(i=0; i<5; i++)
    {
    	for(j=0; j<35; j++)
    	{
    		if(tempOutput2.get(i, j) < 0)
    			tempOutput2.set(i, j, -1);
    		else if(tempOutput2.get(i, j) == 0)
    			tempOutput2.set(i, j, 0);
    		else
    			tempOutput2.set(i, j, 1);
    	}
    }
    System.out.println();
    System.out.println("Actual Input Pattern     Noisy Input Pattern     Expected Output Pattern     Obtained Output Pattern");
    System.out.println("--------------------     -------------------     -----------------------     -----------------------");
    for(i=0; i<5; i++)
    {
    	char ch11, ch22, ch33, ch44;
    	for(j=0; j<35; j=j+5)
    	{
    		for(k=0; k<5; k++)
    		{
        		if (Choice111.get(i, j+k) == 1)
        			ch11 = '#';
        		else if (Choice111.get(i, j+k) == 0)
        			ch11 = 'X';
        		else
        			ch11 = ' ';
        		System.out.print(ch11);
    		}
    		System.out.print("                    ");
    		for(k=0; k<5; k++)
    		{
        		if (tempInput1.get(i, j+k) == 1)
        			ch22 = '#';
        		else if (tempInput1.get(i, j+k) == 0)
        			ch22 = 'X';
        		else
        			ch22 = ' ';
        		System.out.print(ch22);
    		}
    		System.out.print("                    ");
    		for(k=0; k<5; k++)
    		{
        		if (Choice111.get(i, j+k) == 1)
        			ch33 = '#';
        		else if (Choice111.get(i, j+k) == 0)
        			ch33 = 'X';
        		else
        			ch33 = ' ';
        		System.out.print(ch33);
    		}
    		System.out.print("                       ");
    		for(k=0; k<5; k++)
    		{
        		if (tempOutput2.get(i, j+k) == 1)
        			ch44 = '#';
        		else if (tempOutput2.get(i, j+k) == 0)
        			ch44 = '#';
        		else
        			ch44 = ' ';
        		System.out.print(ch44);
    		}
    		System.out.println();
    	}
    	System.out.println();
    }
    ArrayList<Integer> noiseList1 = new ArrayList<Integer>();
    ArrayList<Integer> correctList1 = new ArrayList<Integer>();
    int noiseRate1 = 0, val1 = -1;
    for(i = 0; i < 5; i++)  
    {
    	int check = 0;
	    for(j = 0; j < 35; j++) 
	    {        
	        if(tempOutput2.get(i, j) != Choice111.get(i, j))
	        {
	        ++noiseRate1;
	        if(i == 0)
	        	val1 = 0;
	        if(i == 1)
	        	val1 = 1;
	        if(i == 2)
	        	val1 = 3;
	        if(i == 3)
	        	val1 = 4;
	        if(i == 4)
	        	val1 = 7;
	        noiseList1.add(val1);
	        check = 1;
	        break;
	        } 
	    }
	    if(check == 0)
	    {
	    	if(i == 0)
	        	val1 = 0;
	        if(i == 1)
	        	val1 = 1;
	        if(i == 2)
	        	val1 = 3;
	        if(i == 3)
	        	val1 = 4;
	        if(i == 4)
	        	val1 = 7;
	        correctList1.add(val1);
	    } 
	}
	successRate = 5 - noiseRate1;
	System.out.println("Number of Patterns Correctly Recalled: " + successRate);
	System.out.print("Correct Patterns                     : ");
	for (i=0; i<correctList1.size(); i++)
		System.out.print(correctList1.get(i) + " ");
	System.out.println();
	System.out.print("Incorrect Patterns                   : ");
	for (i=0; i<noiseList1.size(); i++)
		System.out.print(noiseList1.get(i) + " ");
	System.out.println();
	System.out.println("=============================");
	System.out.println();
	
	//Testing Spurious Patterns
    System.out.println("=============================");
    System.out.println("TESTING RESPONSE OF SPURIOUS PATTERNS");
    System.out.println("=============================");
    System.out.println("Using Patterns: 1 4 6 7 9");
	System.out.println("---------------------------");
    Matrix ChoiceA1 = new Matrix(choice18);
    Matrix ChoiceA2 = new Matrix(choice19);
    Matrix ChoiceA3 = new Matrix(choice20);
    for(i=0; i<maxPatterns; i++)
    {
    	for(j=0; j<35; j++)
    	{
    		ChoiceA1.set(i, j, ChoiceA1.get(i, j)*-1);
    	}
    }
    Matrix Bias13 = new Matrix(bias12);
    Matrix WA = new Matrix(weight);
    Matrix tempOutput3 = new Matrix(output12);
    WA = (ChoiceA2.transpose()).times(ChoiceA2);
    for(int bi=0; bi<35; bi++)
	{
		for(int bi2=0; bi2<maxPatterns; bi2++)
		{
			Bias13.set(0, bi, Bias13.get(0, bi)+ChoiceA3.get(bi2, bi));
		}
	}
    tempOutput3 = ChoiceA1.times(WA);
    for(i=0; i<maxPatterns; i++)
    {
    	for(j=0; j<35; j++)
    	{
    		tempOutput3.set(i, j, tempOutput3.get(i, j)+Bias13.get(0, j));
    	}
    }
    for(i=0; i<5; i++)
    {
    	for(j=0; j<35; j++)
    	{
    		if(tempOutput3.get(i, j) < 0)
    			tempOutput3.set(i, j, -1);
    		else if(tempOutput3.get(i, j) == 0)
    			tempOutput3.set(i, j, 0);
    		else
    			tempOutput3.set(i, j, 1);
    	}
    }
    ArrayList<Integer> spuriousList = new ArrayList<Integer>();
    for(i=0; i<5; i++)
    {
    	int check = 0;
    	for(j=0; j<35; j++)
    	{
    		if(tempOutput3.get(i, j) != ChoiceA1.get(i, j))
    		{
    			check = 1;
    			break;
    		}
    	}
    	if(check == 0)
    	{
    		spuriousList.add(i);
    	}
    }
    System.out.println("Spurious Input Pattern     Obtained Output Pattern");
    System.out.println("----------------------     -----------------------");
    for(int l=0; l<spuriousList.size(); l++)
    {
    	i=spuriousList.get(l);
    	{
    		char ch11, ch22;
	    	for(j=0; j<35; j=j+5)
	    	{
	    		for(k=0; k<5; k++)
	    		{
	        		if (ChoiceA1.get(i, j+k) == 1)
	        			ch11 = '#';
	        		else
	        			ch11 = ' ';
	        		System.out.print(ch11);
	    		}
	    		System.out.print("                      ");
	    		for(k=0; k<5; k++)
	    		{
	        		if (tempOutput3.get(i, j+k) == 1)
	        			ch22 = '#';
	        		else
	        			ch22 = ' ';
	        		System.out.print(ch22);
	    		}
	    		System.out.println();
	    	}
	    	System.out.println();
    	}
    }
    System.out.println();
    System.out.println("Using Patterns: 0 1 3 4 7");
	System.out.println("---------------------------");
	Matrix ChoiceA4 = new Matrix(choice21);
    Matrix ChoiceA5 = new Matrix(choice22);
    Matrix ChoiceA6 = new Matrix(choice23);
    for(i=0; i<maxPatterns; i++)
    {
    	for(j=0; j<35; j++)
    	{
    		ChoiceA4.set(i, j, ChoiceA4.get(i, j)*-1);
    	}
    }
    Matrix Bias14 = new Matrix(bias14);
    Matrix WB = new Matrix(weight);
    Matrix tempOutput4 = new Matrix(output13);
    WB = (ChoiceA5.transpose()).times(ChoiceA5);
    for(int bi=0; bi<35; bi++)
	{
		for(int bi2=0; bi2<maxPatterns; bi2++)
		{
			Bias14.set(0, bi, Bias14.get(0, bi)+ChoiceA6.get(bi2, bi));
		}
	}
    tempOutput4 = ChoiceA4.times(WB);
    for(i=0; i<maxPatterns; i++)
    {
    	for(j=0; j<35; j++)
    	{
    		tempOutput4.set(i, j, tempOutput4.get(i, j)+Bias14.get(0, j));
    	}
    }
    for(i=0; i<5; i++)
    {
    	for(j=0; j<35; j++)
    	{
    		if(tempOutput4.get(i, j) < 0)
    			tempOutput4.set(i, j, -1);
    		else if(tempOutput4.get(i, j) == 0)
    			tempOutput4.set(i, j, 0);
    		else
    			tempOutput4.set(i, j, 1);
    	}
    }
    ArrayList<Integer> spuriousList1 = new ArrayList<Integer>();
    for(i=0; i<5; i++)
    {
    	int check = 0;
    	for(j=0; j<35; j++)
    	{
    		if(tempOutput4.get(i, j) != ChoiceA4.get(i, j))
    		{
    			check = 1;
    			break;
    		}
    	}
    	if(check == 0)
    	{
    		spuriousList1.add(i);
    	}
    }
    System.out.println("Spurious Input Pattern     Obtained Output Pattern");
    System.out.println("----------------------     -----------------------");
    for(int l=0; l<spuriousList1.size(); l++)
    {
    	i=spuriousList1.get(l);
    	{
    		char ch11, ch22;
	    	for(j=0; j<35; j=j+5)
	    	{
	    		for(k=0; k<5; k++)
	    		{
	        		if (ChoiceA4.get(i, j+k) == 1)
	        			ch11 = '#';
	        		else
	        			ch11 = ' ';
	        		System.out.print(ch11);
	    		}
	    		System.out.print("                      ");
	    		for(k=0; k<5; k++)
	    		{
	        		if (tempOutput4.get(i, j+k) == 1)
	        			ch22 = '#';
	        		else
	        			ch22 = ' ';
	        		System.out.print(ch22);
	    		}
	    		System.out.println();
	    	}
	    	System.out.println();
    	}
    }
    System.out.println("=============================");
	}
}