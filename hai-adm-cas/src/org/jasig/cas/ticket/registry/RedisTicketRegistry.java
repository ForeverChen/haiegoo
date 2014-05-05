package org.jasig.cas.ticket.registry;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.jasig.cas.ticket.Ticket;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

/*  
 *  TicketRegistry using Redis, to solve CAS Cluster.  
 *  @author ZL
 */
public class RedisTicketRegistry extends AbstractDistributedTicketRegistry {
	
	private RedisTemplate<String, Ticket> redisTemplate;	//redis客户端对象
	private long tgtTimeout;								//TGT timeout in seconds
	private long stTimeout;									//ST timeout in seconds
	private final String CAS_TICKET = "CAS_TICKET";
	

	@Override
	public Ticket getTicket(final String ticketId) {
        if (ticketId == null) {
            return null;
        }

        if (log.isDebugEnabled()) {
            log.debug("Attempting to retrieve ticket [" + ticketId + "]");
        }

		Ticket ticket = redisTemplate.opsForValue().get(CAS_TICKET +"/"+ ticketId);
        if (ticket != null) {
            log.debug("Ticket [" + ticketId + "] found in registry.");
        }

		return getProxiedTicketInstance(ticket);
	}

	@Override
	public Collection<Ticket> getTickets() {
		throw new UnsupportedOperationException("GetTickets not supported.");
	}

	@Override
	public void addTicket(Ticket ticket) {
		this.updateTicket(ticket);
	}

	@Override
	protected void updateTicket(Ticket ticket) {
        Assert.notNull(ticket, "ticket cannot be null");

        if (log.isDebugEnabled()) {
            log.debug("Added ticket [" + ticket.getId() + "] to registry.");
        }

		long timeout;
		if (ticket instanceof TicketGrantingTicket)
			timeout = tgtTimeout;
		else
			timeout = stTimeout;
        
		redisTemplate.opsForValue().set(CAS_TICKET +"/"+ ticket.getId(), ticket, timeout, TimeUnit.SECONDS);
	}

	@Override
	public boolean deleteTicket(final String ticketId) {
        if (ticketId == null) {
            return false;
        }
        if (log.isDebugEnabled()) {
            log.debug("Removing ticket [" + ticketId + "] from registry");
        }
        
        redisTemplate.delete(CAS_TICKET +"/"+ ticketId);
        return !redisTemplate.hasKey(CAS_TICKET +"/"+ ticketId);
	}

	@Override
	protected boolean needsCallback() {
		return false;
	}	
	

	public RedisTemplate<String, Ticket> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Ticket> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public long getTgtTimeout() {
		return tgtTimeout;
	}

	public void setTgtTimeout(long tgtTimeout) {
		this.tgtTimeout = tgtTimeout;
	}

	public long getStTimeout() {
		return stTimeout;
	}

	public void setStTimeout(long stTimeout) {
		this.stTimeout = stTimeout;
	}

}