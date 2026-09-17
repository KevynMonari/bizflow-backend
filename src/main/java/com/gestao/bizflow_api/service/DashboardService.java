package com.gestao.bizflow_api.service;

import com.gestao.bizflow_api.dto.DashboardResponseDTO;
import com.gestao.bizflow_api.model.ItemVenda;
import com.gestao.bizflow_api.model.StatusVenda;
import com.gestao.bizflow_api.model.Venda;
import com.gestao.bizflow_api.repository.VendaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class DashboardService {

    private final VendaRepository vendaRepository;

    public DashboardService(VendaRepository vendaRepository){
        this.vendaRepository = vendaRepository;
    }

    public DashboardResponseDTO obterResumoDiario(LocalDate data){
        LocalDate dataConsulta = (data != null) ? data : LocalDate.now();

        LocalDateTime inicio = dataConsulta.atStartOfDay();
        LocalDateTime fim = dataConsulta.atTime(LocalTime.MAX);

        List<Venda> vendasDoDia = vendaRepository.findByDataHoraBetweenAndStatus(inicio, fim, StatusVenda.CONCLUIDA);

        BigDecimal faturamentoTotal = BigDecimal.ZERO;
        BigDecimal custoTotal = BigDecimal.ZERO;

        for (Venda venda : vendasDoDia){
            faturamentoTotal = faturamentoTotal.add(venda.getValorTotal());

            for (ItemVenda item : venda.getItens()){
                BigDecimal precoCusto = (item.getProduto() != null && item.getProduto().getPrecoCusto() != null)
                        ? item.getProduto().getPrecoCusto()
                        : BigDecimal.ZERO;

                BigDecimal custoItem = precoCusto.multiply(BigDecimal.valueOf(item.getQuantidade()));
                custoTotal = custoTotal.add(custoItem);
            }
        }

        BigDecimal lucroLiquido = faturamentoTotal.subtract(custoTotal);

        BigDecimal margemLucro = BigDecimal.ZERO;
        if (faturamentoTotal.compareTo(BigDecimal.ZERO) > 0){
            margemLucro = lucroLiquido.divide(faturamentoTotal, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);
        }

        return new DashboardResponseDTO(
                faturamentoTotal,
                custoTotal,
                lucroLiquido,
                (long) vendasDoDia.size(),
                margemLucro
        );
    }
}
