package it.polito.tdp.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.PriorityQueue;

import it.polito.tdp.noleggio.model.Event.EventType;

public class Simulatore {
  // Parametri di ingresso
  private int NC;
  private Duration T_IN = Duration.ofMinutes(10); //tempo dell'intervallo, uso la classe Duration
  private Duration T_TRAVELDuration = Duration.ofHours(1); //1, 2  o 3 volte tanto
  
  // Valori calcolati in uscita
  private int nClientiTot;
  private int nClientiInsoddisfatti;
	
  // Stato del mondo
  private int autoDisponibili;
  
  // Coda degli eventi
  private PriorityQueue<Event> queue ;
  
  //Costruttore
  public Simulatore(int NC) {
	  this.NC = NC;
	  this.queue = new PriorityQueue<>();
	  this.autoDisponibili = NC;
  }
  
  //run gira in loop fino a quando la coda non è vuota, processando ogni evento
  public void run() {
	 while(this.queue.isEmpty()) {
	 Event e = this.queue.poll();
	 processEvent(e);
	 }
	 
  }
  
  public void caricaEventi() {
	  LocalTime oraLocalTime = LocalTime.of(8, 0);
	  while(oraLocalTime.isBefore(LocalTime.of(16, 0))) {
		  this.queue.add(new Event(oraLocalTime, EventType.NUOVO_CLIENTE));
		  oraLocalTime=oraLocalTime.plus(T_IN);
		  
	  }
  }

private void processEvent(Event e) {
	switch (e.getType()) {
	case NUOVO_CLIENTE:
		this.nClientiTot++;
		if(this.autoDisponibili>0) {
			this.autoDisponibili--;
			int ore = (int)(Math.random()*3)+1;
			LocalTime oraRientro = e.getTime().plus(Duration.ofHours(ore*T_TRAVELDuration.toHours()));
			this.queue.add(new Event(oraRientro, EventType.AUTO_RESTITUITA));
		} else {
			this.nClientiInsoddisfatti++;
		}
		break;

	case AUTO_RESTITUITA:
		this.autoDisponibili++;
		break;
	}
	
	
}

public int getNC() {
	return NC;
}

public void setNC(int nC) {
	NC = nC;
}

public Duration getT_IN() {
	return T_IN;
}

public void setT_IN(Duration t_IN) {
	T_IN = t_IN;
}

public Duration getT_TRAVELDuration() {
	return T_TRAVELDuration;
}

public void setT_TRAVELDuration(Duration T_TRAVELDuration) {
	this.T_TRAVELDuration = T_TRAVELDuration;
}

public int getnClientiTot() {
	return nClientiTot;
}

public void setnClientiTot(int nClientiTot) {
	this.nClientiTot = nClientiTot;
}

public int getnClientiInsoddisfatti() {
	return nClientiInsoddisfatti;
}

public void setnClientiInsoddisfatti(int nClientiInsoddisfatti) {
	this.nClientiInsoddisfatti = nClientiInsoddisfatti;
}

public int getAutoDisponibili() {
	return autoDisponibili;
}

public void setAutoDisponibili(int autoDisponibili) {
	this.autoDisponibili = autoDisponibili;
}

public PriorityQueue<Event> getQueue() {
	return queue;
}

public void setQueue(PriorityQueue<Event> queue) {
	this.queue = queue;
}
  
  
  
}
