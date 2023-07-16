package appunti_esame;

import java.util.List;

import org.jgrapht.Graphs;

public class TestGrafi {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		crazioneGrafi model = new crazioneGrafi();
		
		Persona p1 = new Persona("1", "verdi", "biondi");
		Persona p2 = new Persona("2", "verdi", "castani");
		Persona p3 = new Persona("3", "marroni", "castani");
		Persona p4 = new Persona("4", "azzurri", "castani");
		Persona p5 = new Persona("5", "azzurri", "biondi");
		Persona p6 = new Persona("6", "marroni", "rossi");
		Persona p7 = new Persona("7", "marroni", "biondi");
		
		
//		if(p1.equals(p1))
//			System.out.println("p1 e p1 uguali");
//		if(p1.equals(p2))
//			System.out.println("p1 e p2 uguali");
//		else
//			System.out.println("p1 e p2 diversi");

		
		model.listaPersone.add(p1);
		model.listaPersone.add(p2);
		model.listaPersone.add(p3);
		model.listaPersone.add(p4);
		model.listaPersone.add(p5);
		model.listaPersone.add(p6);
		model.listaPersone.add(p7);


//		model.creaGrafo();
//		System.out.println(model.listaCoppie);
//		System.out.println(model.grafo.vertexSet());
//		System.out.println(model.grafo.edgeSet());

		//v1
//		System.out.println("v1:\n");
//		System.out.println("grafo connesso di p1: \n"+
//						model.getRaggiungibiliV1(p1));
//		System.out.println("grafo connesso di p4: \n"+
//				model.getRaggiungibiliV1(p4));
//		System.out.println("\n\n");

		//v2
		
//		System.out.println("v2:\n");
//		System.out.println("grafo connesso di p1: \n"+
//				model.getRaggiungibiliV2(p1));
//		System.out.println("grafo connesso di p4: \n"+
//				model.getRaggiungibiliV2(p4));
//		System.out.println("\n\n");

		System.out.println("creazione grafo pesato...");
		model.creaGrafoPesato();
		System.out.println("grafo pesato creato: \n\n");
		System.out.println("lista vertici: "+model.grafoPesato.vertexSet());
		System.out.println("lista archi : \n");
		for(CoppiaPersone cp : model.grafoPesato.edgeSet()) {
			System.out.println(cp+"   peso: "+model.grafoPesato.getEdgeWeight(cp));
		}
		System.out.println("\n\n");
		

		
		//connInspector
		System.out.println("connInspector:\n");
		System.out.println("grafo connesso di p1: \n"+
						model.getGrafoConnesso(model.grafoPesato, p1));
//		System.out.println("grafo connesso di p4: \n"+
//				model.getGrafoConnesso(p4));
		
		System.out.println("\n");
		System.out.println("cammino minimo da p4 a p7");
		System.out.println(model.getCamminoMinimo(p4, p7));
		System.out.println("peso cammino: "+model.pesoCammino);

		System.out.println("\nadiacenti a p1:\n");
		List<Persona>viciniP1 = Graphs.neighborListOf(model.grafoPesato, p1);
		System.out.println(viciniP1);
		
		System.out.println("\n\n");
		System.out.println("cammino massimo tra p4 e p7: ");
		System.out.println(model.getCamminoMax(p4, p7));
		System.out.println("peso cammino max: "+model.pesoMax);

		System.out.println("\n\n");
		System.out.println("cammino massimo senza cicli: ");
		System.out.println(model.getCamminoMaxNoCicli());
		System.out.println("peso cammino max: "+model.pesoMax2);

		//Persona p8 = new Persona("8", "ciao", "ciaociao");
		//System.out.println(model.haCicli(model.listaPersone, p8));
	}

}
