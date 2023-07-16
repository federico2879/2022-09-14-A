package appunti_esame;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

/*									DOCUMENTAZIONE
 * 
 *  grafi: https://jgrapht.org/javadoc/org.jgrapht.core/module-summary.html
 * 
 * 
 */

public class crazioneGrafi {
	
	
	
	
	List<Persona> listaPersone = new ArrayList<Persona>();
	Graph<Persona, CoppiaPersone> grafo = new SimpleGraph<Persona, CoppiaPersone>(CoppiaPersone.class);
	List<CoppiaPersone> listaCoppie = new ArrayList<CoppiaPersone>();

	
	//CREAZIONE GRAFO
	public void creaGrafo() {
		Graphs.addAllVertices(this.grafo, this.listaPersone);
		
		
		for(CoppiaPersone cpp : this.listaCoppie) {
			this.grafo.addEdge(cpp.p1, cpp.p2, cpp);
		}
		
	}
	
	Graph<Persona, CoppiaPersone> grafoPesato = new SimpleWeightedGraph<Persona, CoppiaPersone>(CoppiaPersone.class);
	
	public void creaGrafoPesato() {
		Graphs.addAllVertices(this.grafoPesato, this.listaPersone);
		
		this.creaCoppie();
		for(CoppiaPersone cpp : this.listaCoppie) {
			
			this.grafoPesato.addEdge(cpp.p1, cpp.p2, cpp);
			Double peso = (double)(Integer.parseInt(cpp.p1.id) + Integer.parseInt(cpp.p2.id));
			this.grafoPesato.setEdgeWeight(cpp, peso);

			//System.out.println(this.grafoPesato.getEdgeWeight(cpp));
			
		}
	}
	
	public void creaCoppie() {
		for(Persona p1 : this.listaPersone) {
			for(Persona p2 : this.listaPersone) {
				if((p1.coloreOcchi.compareTo(p2.coloreOcchi)==0 
							|| p1.coloreCapelli.compareTo(p2.coloreCapelli)==0) && 
						!p1.equals(p2)) {
					CoppiaPersone cp = new CoppiaPersone(p1, p2);
					if(!this.listaCoppie.contains(cp))
						this.listaCoppie.add(new CoppiaPersone(p1, p2));
					//System.out.println(this.listaCoppie);
				}
					
			}
		}
	}
	
	//OTTENIMENTO ADIACENTI A VERTICE
	
	public List<Persona> getAdiacenti(Graph<Persona, CoppiaPersone> g, Persona p){
		
		return Graphs.neighborListOf(g, p);
	}
	
	//ESPLORAZIONE GRAFO
	
	/*  risorse
	 *  	https://jgrapht.org/javadoc/org.jgrapht.core/org/jgrapht/traverse/package-summary.html
	 */
	
	// caso 1: BreadthFirstIterator ( AMPIEZZA)
	
	public List<Persona> getRaggiungibiliV1(Persona start) {
											// do in input grafo e vertice di partenza
		BreadthFirstIterator<Persona, CoppiaPersone> bf = 
				                     new BreadthFirstIterator<>(this.grafo, start);

		List<Persona> raggiungibili = new ArrayList<Persona>();

		while (bf.hasNext()) {
			Persona p = bf.next();
			raggiungibili.add(p);
		}
		
		return raggiungibili;
	}
	
	
	//caso 2: DepthFirstIterator (PROFONDITA')
	
	public List<Persona> getRaggiungibiliV2(Persona start) {
		// do in input grafo e vertice di partenza
		DepthFirstIterator<Persona, CoppiaPersone> df = new DepthFirstIterator(this.grafo, start);

		List<Persona> raggiungibili = new ArrayList<Persona>();

		while (df.hasNext()) {
			Persona p = df.next();
			raggiungibili.add(p);
		}

		return raggiungibili;
	}
	
	// OSSERVAZIONE: 
	//  è meglio utilizzare bf se si vuole eseguire una ricerca in ampiezza, per esempio trovare
	//     l'albero di ricerca (trovare grafo connesso)
	// è meglio utilizzare df se si vuole trovare un percorso tra due nodi specifici, in quanto
	//     df esegue un percorso alla volta, e nel caso in cui non si cerchi l'ottimo arriva in 
	//     fretta alla soluzione
	
	
	// per cercare un grafo (o tutti i grafi) connesso si puo utilizzare:
	// ConnectivityInspector(Graph<V,E> g)
	
	public Set<Persona> getGrafoConnesso (Graph<Persona, CoppiaPersone> g, Persona p){
		
		Set<Persona> connessi = new HashSet<Persona>();
		
		ConnectivityInspector<Persona, CoppiaPersone> c = new ConnectivityInspector<>(g);
		
		connessi = c.connectedSetOf(p);
		
		return connessi;
	}
	
	
	//  CAMMINI MINIMI 
	// Interfaccia ShortestPathAlgorithm<V,E>
	
	
	
	// Utilizzo algoritmo di Dijkstra : DijkstraShortestPath<V,​E>
	
	double pesoCammino = 0.0;
	
	public List<Persona> getCamminoMinimo(Persona start, Persona end) {
		
		List<Persona> ritorno = new ArrayList<Persona>();
		//DijkstraShortestPath<Persona,​ CoppiaPersone> ss = new DijkstraShortestPath<>(this.grafoPesato);
		
		DijkstraShortestPath<Persona, CoppiaPersone> sp = new DijkstraShortestPath<>(this.grafoPesato);
		
		GraphPath<Persona, CoppiaPersone> path = sp.getPath(start, end);
		pesoCammino = sp.getPathWeight(start, end);
		
		if(path!=null)
			return path.getVertexList();
		else
			return ritorno;
	}
	
	
	// CAMMINO MASSIMO LUNGO TRA 2 VERTICI
	List<Persona> soluzione1;
	double pesoMax;

	public List<Persona> getCamminoMax(Persona start, Persona end){
		
		List<Persona> parziale = new ArrayList<Persona>();
		parziale.add(start);
		
		soluzione1 =  new ArrayList<Persona>();
		pesoMax = 0;
		
		ricerca(parziale, start, end, 0.0);
		return soluzione1;
	}
	
	public void ricerca(List<Persona> parziale, Persona p, Persona end, Double peso) {
		
		if(p.equals(end)) {
			if(peso>pesoMax) {
				soluzione1 = new ArrayList<Persona>(parziale);
				pesoMax = peso;
			}
			
			return;
		}
		
		List<Persona> adiacenti = Graphs.neighborListOf(this.grafoPesato, p);
		
		for(Persona pp : adiacenti) {
			if(!parziale.contains(pp)) {
				
				parziale.add(pp);
				CoppiaPersone arco = this.grafoPesato.getEdge(p, pp);
				peso += this.grafoPesato.getEdgeWeight(arco);
				
				ricerca(parziale, pp, end, peso);
				
				Persona rimosso = parziale.remove(parziale.size()-1);
				CoppiaPersone arcoRimosso = 
						this.grafoPesato.getEdge(parziale.get(parziale.size()-1), rimosso);
				peso -= this.grafoPesato.getEdgeWeight(arcoRimosso);
				
			}
		}
	} 
	
	
	//CAMMINO MAX SENZA CICLI
	
	List<Persona> soluzione2;
	List<CoppiaPersone> soluzioneArchi2;
	double pesoMax2;

	public List<CoppiaPersone> getCamminoMaxNoCicli(){
		
		soluzione2 =  new ArrayList<Persona>();
		soluzioneArchi2 = new ArrayList<CoppiaPersone>();
		pesoMax2 = 0;
		
		for(Persona p : this.grafoPesato.vertexSet()) {
			List<Persona> parziale = new ArrayList<Persona>();
			parziale.add(p);
			
			List<CoppiaPersone> parzArchi = new ArrayList<CoppiaPersone>();
			
			
			ricerca2(parziale,parzArchi, p, 0.0);
			
		}
		return soluzioneArchi2;
	}
	
	public void ricerca2(List<Persona> parziale,List<CoppiaPersone> parzArchi, Persona p, Double peso) {
		
		
		if(peso>pesoMax2) {
			soluzione2 = new ArrayList<Persona>(parziale);
			soluzioneArchi2 = new ArrayList<CoppiaPersone>(parzArchi);
			pesoMax2 = peso;
		}
			
		
		
		List<Persona> adiacenti = Graphs.neighborListOf(this.grafoPesato, p);
		
		for(Persona pp : adiacenti) {
			if(!parziale.contains(pp) && !this.haCicli2(parziale,parzArchi,p, pp)) {
				
				parziale.add(pp);
				CoppiaPersone arco = this.grafoPesato.getEdge(p, pp);
				parzArchi.add(arco);
				peso += this.grafoPesato.getEdgeWeight(arco);
				
				ricerca2(parziale,parzArchi, pp, peso);
				
				Persona rimosso = parziale.remove(parziale.size()-1);
				CoppiaPersone arcoRimosso = 
						this.grafoPesato.getEdge(parziale.get(parziale.size()-1), rimosso);
				
				peso -= this.grafoPesato.getEdgeWeight(arcoRimosso);
				parzArchi.remove(arcoRimosso);
				
			}
		}
		return;
	} 
	
	public boolean haCicli(List<Persona> parziale,List<CoppiaPersone>parzArchi,Persona ultimo, Persona daAgg) {
		
		List<Persona> nuovo_parziale = new ArrayList<Persona>(parziale);
		nuovo_parziale.add(daAgg);
		CoppiaPersone arco = this.grafoPesato.getEdge(ultimo, daAgg);
		List<CoppiaPersone> nuovo_parzArchi = new ArrayList<CoppiaPersone>(parzArchi);
		nuovo_parzArchi.add(arco);
		if(nuovo_parzArchi.size()>=nuovo_parziale.size())
			return true;
		else 
			return false;
		
		
		
//		List<Persona> verticiTemp = new ArrayList<Persona>(parziale);
//		verticiTemp.add(daAgg);
//		Set<CoppiaPersone> archiTemp = new HashSet<CoppiaPersone>();
//		for(CoppiaPersone cp : this.grafoPesato.edgeSet()) {
//			if(verticiTemp.contains(cp.p1) && verticiTemp.contains(cp.p2))
//				archiTemp.add(cp);
//		}
//		if(archiTemp.size()>=verticiTemp.size())
//			return true;
//		else
//			return false;
		
	}
	
public boolean haCicli2(List<Persona> parziale,List<CoppiaPersone>parzArchi,Persona ultimo, Persona daAgg) {
		
//		List<Persona> nuovo_parziale = new ArrayList<Persona>(parziale);
//		nuovo_parziale.add(daAgg);
//		CoppiaPersone arco = this.grafoPesato.getEdge(ultimo, daAgg);
//		List<CoppiaPersone> nuovo_parzArchi = new ArrayList<CoppiaPersone>(parzArchi);
//		nuovo_parzArchi.add(arco);
//		if(nuovo_parzArchi.size()>=nuovo_parziale.size())
//			return true;
//		else 
//			return false;
		
		
		
		List<Persona> verticiTemp = new ArrayList<Persona>(parziale);
		verticiTemp.add(daAgg);
		Set<CoppiaPersone> archiTemp = new HashSet<CoppiaPersone>();
		for(CoppiaPersone cp : this.grafoPesato.edgeSet()) {
			if(verticiTemp.contains(cp.p1) && verticiTemp.contains(cp.p2))
				archiTemp.add(cp);
		}
		if(archiTemp.size()>=verticiTemp.size())
			return true;
		else
			return false;
		
	}

	
	
}

