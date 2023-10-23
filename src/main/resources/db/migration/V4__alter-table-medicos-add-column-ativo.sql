alter table medicos add ativo tinyint; -- altera tabela (add nova coluna)
update medicos set ativo = 1; -- atualiza tabela (registros antigos) para registros ativos = true (1)