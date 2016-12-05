# TSP-Search-Algorithm

## Project Structure
\TSP-Search-Algorithm  
&emsp;&emsp;\DATA  
&emsp;&emsp;&emsp;&emsp;\input data files  
&emsp;&emsp;\output  
&emsp;&emsp;&emsp;&emsp;\output files.   
&emsp;&emsp;\src  
&emsp;&emsp;&emsp;&emsp;\uf  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;\UF.java  
&emsp;&emsp;&emsp;&emsp;\TwoOptExchange.java  
&emsp;&emsp;&emsp;&emsp;\TSPMSTApproximation.java  
&emsp;&emsp;&emsp;&emsp;\TSPAnswer.java  
&emsp;&emsp;&emsp;&emsp;\TSP.java  
&emsp;&emsp;&emsp;&emsp;\SimulatedAnnealing.java  
&emsp;&emsp;&emsp;&emsp;\Node.java  
&emsp;&emsp;&emsp;&emsp;\MyBranchAndBound.java  
&emsp;&emsp;&emsp;&emsp;\DummyNode.java  
&emsp;&emsp;&emsp;&emsp;\DataParser.java  
&emsp;&emsp;&emsp;&emsp;\BnB.java  

This project contains 5 algorithms. They are `Branch and Bound`, `Simulated Annealing`, `2-Opt Exchange`, `Approximate MST` and `Nearest Neighbor`.

`DataParser.java` is used to convert input file into a geo-matrix and write result into output file.

`TSP.java` is the main function of this project which decides which algorithm will be called according to user's input.

`Branch and Bound` algorithm contains 4 `.java` files, which are `BnB.java`, `DummyNode.java`, `MyBranchAndBound.java` and `Node.java`.

`Simulated Annealing` and `2-OPT Exchange` are written in `SimulatedAnnealing.java` and `TwoOptExchange.java` separately.

`Approximate MST` contains a `uf` package which implement union and find algorithm. Its main algorithm is in `TSPMSTApproximation.java`.

`Nearest Neighbor` is written in `TSP.java`.

## How to excute this project

### Complie Code

```bash
javac <filename>.java
```  

### How to run

```bash
java TSP inst<filename> alg[BnB|MSTApprox|NN|SA|TO] time<cutoff_in_seconds> seed<random_seed>
```  

If you just want to run `Branch and Bound` algorithm, you can ignore the last argument.  
```bash
java TSP inst<filename> BnB time<cutoff_in_seconds>
``` 

If you want to run `Appoximate MST` algorithm or `Nearest Neighbor` alogrithm, you can just use the first two arguments.
```bash
java TSP inst<filename> alg[MSTApprox|NN]
```


